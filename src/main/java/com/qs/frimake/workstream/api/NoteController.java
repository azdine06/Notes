package com.qs.frimake.workstream.api;

import com.qs.frimake.workstream.entity.Note;
import com.qs.frimake.workstream.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {


    private final NoteService noteService;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note create(@Validated @RequestBody Note note, @RequestParam boolean isModerator) {
        return noteService.createNote(note, isModerator);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @PutMapping("/{id}")
    public Note update(@PathVariable Long id, @Validated @RequestBody Note note) {
        return noteService.updateNote(id, note);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        noteService.deleteNote(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @GetMapping
    public Page<Note> listNotes(
            @RequestParam String targetEntity,
            @RequestParam String targetEntityId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return noteService.listNotes(targetEntity, targetEntityId, page, size);
    }
}
