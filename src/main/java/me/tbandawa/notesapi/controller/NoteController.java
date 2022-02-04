package me.tbandawa.notesapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import me.tbandawa.notesapi.exception.NoteNotFoundException;
import me.tbandawa.notesapi.model.Note;
import me.tbandawa.notesapi.repository.NoteRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {
	
	 @Autowired
	 NoteRepository noteRepository;
	 
	 // Get All Notes
	 @GetMapping("/notes")
	 public List<Note> getAllNotes() {
	     return noteRepository.getNotes();
	 }
	 
	 // Create a new Note
	 @PostMapping("/notes")
	 public Note createNote(@Valid @RequestBody Note note) {
	     return noteRepository.save(note);
	 }
	 
	 // Get a Single Note
	 @GetMapping("/notes/{id}")
	 public Note getNoteById(@PathVariable(value = "id") Long noteId) throws NoteNotFoundException {
		 return noteRepository.findById(noteId)
	             .orElseThrow(() -> new NoteNotFoundException("Note", "id", noteId));
	 }
	 
	 // Update a Note
	 @PutMapping("/notes/{id}")
	 public Note updateNote(@PathVariable(value = "id") Long noteId,
	                                         @Valid @RequestBody Note noteDetails) throws NoteNotFoundException {
		 Note note = noteRepository.findById(noteId)
	             .orElseThrow(() -> new NoteNotFoundException("Note", "id", noteId));
	     note.setTitle(noteDetails.getTitle());
	     note.setContent(noteDetails.getContent());
	     Note updatedNote = noteRepository.save(note);
	     return updatedNote;
	 }
	 
	 // Delete a Note
	 @DeleteMapping("/notes/{id}")
	 public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) throws NoteNotFoundException {
		 Note note = noteRepository.findById(noteId)
	             .orElseThrow(() -> new NoteNotFoundException("Note", "id", noteId));
	     noteRepository.delete(note);
	     return ResponseEntity.ok().build();
	 }

}
