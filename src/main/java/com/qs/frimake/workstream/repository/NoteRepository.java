package com.qs.frimake.workstream.repository;

import com.qs.frimake.workstream.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;






import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findByTargetEntityAndTargetEntityId(String targetEntity, String targetEntityId, Pageable pageable);

    Page<Note> findByTargetEntityAndTargetEntityIdAndCreatedBy(String targetEntity, String targetEntityId, String createdBy, Pageable pageable);
}
