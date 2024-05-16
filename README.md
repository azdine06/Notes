version: '3.7'

services:
postgres:
container_name: font_client_docker
image: postgres:16.1
environment:
POSTGRES_PASSWORD: mysecretpassword
POSTGRES_DB: keycloak
POSTGRES_USER: keycloak
PGDATA: /var/lib/postgresql/data/pgdata
ports:
- "5433:5432"
volumes:
- ./data:/var/lib/postgresql/data
networks:
- local_network

networks:
local_network:
external: true



package com.qs.frimake.workstream.api;

import jakarta.validation.Valid;
import com.qs.frimake.workstream.entity.Note;
import com.qs.frimake.workstream.service.NoteService;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/note")
public class NoteController {

    private NoteService noteService;

@PreAuthorize("hasRole('restrictedToModerators')")
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Note create(@RequestBody Note note, @PathVariable boolean isModerateur) {
return noteService.createNote(note, isModerateur);
}

    @PutMapping("notes/{id}")
    public Note update(@PathVariable Long id, @Valid @RequestBody Note note) {
        return noteService.update(id,note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        noteService.remove(id);
    }

}



package com.qs.frimake.workstream.service;


import com.qs.frimake.workstream.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import com.qs.frimake.workstream.entity.Note;

import org.hibernate.envers.Audited;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Audited
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;


    // cette methode c'est pour crée un Note
    public Note createNote(Note note, boolean isRestrictedToModerators) {
        note.setRestrictedToModerators(isRestrictedToModerators);
        return noteRepository.save(note);
    }

//    // cette methode c'est pour crée un Note
//    public Note createNote(Note note, boolean responsable) {
//        if(responsable){
//            note.setRestrictedToModerators(true);
//        }
//        return noteRepository.save(note);
//    }

    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }

    public Note update(Long id, Note note) {

        Note note1 = noteRepository.findById(id).orElseThrow();
        if (!note1.isRestrictedToModerators()) {
            throw new RuntimeException("mq");
        }
        return noteRepository.save(note);
    }

    public void remove(Long id, Note note, User user) {
        // Récupérer la note à supprimer par son identifiant
        Note noteToDelete = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));

        // Vérifier les droits de l'utilisateur
        if (noteToDelete.isRestrictedToModerators()) {
            if (!user.isModerator()) {
                throw new RuntimeException("You do not have permission to delete this note");
            }
        } else {
            // Vérifier si l'utilisateur est le créateur de la note
            if (!noteToDelete.getCreator().equals(user)) {
                throw new RuntimeException("You do not have permission to delete this note");
            }
        }

        // Supprimer la note
        noteRepository.deleteById(id);
    }
}
//

//    //  Method to update a note
//    public Note updateNote(Long id, Note updatedNote) {
//
//        return null;
//    }
//
//    // Méthode pour supprimer une note
//    public void deleteNote(Long id) {
//        // Logique pour supprimer une note
//    }
//
//    // Méthode pour lister les notes
//    public Page<Note> listNotes(Pageable pageable) {
//        PageRequest createdDate = PageRequest.of(0, 50,
//                Sort.by("createdDate").descending());
//        System.out.println(createdDate);
//        // Logique pour lister les notes
//        return (Page<Note>) createdDate;
//    }
//
//}
//
//private final PatientRepository patientRepository;
//
//public PatientService(PatientRepository patientRepository) {
//    this.patientRepository = patientRepository;
//}
//
//public Page<Patient> search(Pageable pageable) {
//
//    return patientRepository.findAll(pageable);
//}
//
//public Optional<Patient> findById(Long id) {
//    return patientRepository.findById(id);
//}
//
//public Patient create(Patient patient) {
//    return patientRepository.save(patient);
//}
//
//public Patient update(Long id, Patient patient) {
//    patient.setId(id);
//    return patientRepository.save(patient);
//}
//
//public void remove(Long id) {
//    patientRepository.deleteById(id);
//}
//
//public List<Patient> findByGender(Patient.Gender gender) {
//    return patientRepository.findByGender(gender);
//}
//
//public Page<Patient> findByGender(Patient.Gender gender, Pageable pageable) {
//    return patientRepository.findByGender(gender, pageable);
//}
//}



