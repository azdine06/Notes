package com.qs.frimake.workstream.service;

import com.qs.frimake.workstream.entity.Note;
import com.qs.frimake.workstream.entity.UserService;
import com.qs.frimake.workstream.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.expression.ExpressionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    public Note createNote(Note note, boolean isModerator) {
        note.setRestrictedToModerators(isModerator);
        return noteRepository.save(note);
    }

    public Note updateNote(Long id, Note noteDetails) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new ExpressionException("Note not found"));
        boolean isModerator = userService.isCurrentUserModerator();

        if (!isModerator && note.isRestrictedToModerators()) {
            throw new AccessDeniedException("Non-moderators cannot update restricted notes.");
        }

        if (!isModerator && !note.getCreatedBy().equals(userService.getCurrentUserId())) {
            throw new AccessDeniedException("Non-moderators can only update their own notes.");
        }

        note.setContent(noteDetails.getContent());
        note.setTargetEntity(noteDetails.getTargetEntity());
        note.setTargetEntityId(noteDetails.getTargetEntityId());

        return noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new ExpressionException("Note not found"));
        boolean isModerator = userService.isCurrentUserModerator();

        if (!isModerator && note.isRestrictedToModerators()) {
            throw new AccessDeniedException("Non-moderators cannot delete restricted notes.");
        }

        if (!isModerator && !note.getCreatedBy().equals(userService.getCurrentUserId())) {
            throw new AccessDeniedException("Non-moderators can only delete their own notes.");
        }

        noteRepository.delete(note);
    }
    public Page<Note> listNotes(String targetEntity, String targetEntityId, int page, int size) {
        boolean isModerator = userService.isCurrentUserModerator();
        String currentUserId = userService.getCurrentUserId();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        if (isModerator) {
            return noteRepository.findByTargetEntityAndTargetEntityId(targetEntity, targetEntityId, pageable);
        } else {
            return noteRepository.findByTargetEntityAndTargetEntityIdAndCreatedBy(targetEntity, targetEntityId, currentUserId, pageable);
        }
    }
}

