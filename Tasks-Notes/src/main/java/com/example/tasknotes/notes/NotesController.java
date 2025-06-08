package com.example.tasknotes.notes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("")
    public ResponseEntity<List<NotesDto>> getAllNotes() {
        var notes = notesService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotesDto> getNoteById(@PathVariable Long id) {
        var note = notesService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    @PostMapping("")
    public ResponseEntity<NotesDto> createNote(@RequestBody NotesDto dto) {
        var savedNote = notesService.createNote(dto);
        return ResponseEntity.created(URI.create("/notes/" + savedNote.getId()))
                .body(savedNote);
    }
}