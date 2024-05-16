package com.qs.frimake.workstream.entity;

import com.qs.frimake.workstream.config.jpa.entity.CustomAbstractAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;


@Audited
@Entity
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Note extends CustomAbstractAuditable<Long> {

    /**
     * The identifier of the target entity this note is associated with.
     */
    @Column(nullable = false)
    private String targetEntityId;


    /**
     * The content of the note.
     */
    @Column(nullable = false, length = 500)
    private String content;


    /**
     * Specifies if the note is visible only to moderators. If true, only moderators can view this note.
     * If false, it is visible to the user who created it.
     */

    @Column(nullable = false)
    private boolean restrictedToModerators;


    /**
     * The generic foreign key to associate this note with any object like User, Ticket, Activity, etc.
     * Storing the class name of the target entity.
     */
//    @ManyToOne
    @Column(nullable = false)
//    @JoinColumn(name = "target_entity_id")
    private String targetEntity;
}
