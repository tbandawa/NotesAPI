package me.tbandawa.notesapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.tbandawa.notesapi.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> { 
	
	@Query("select n from Note n order by n.id desc")
	public List<Note> getNotes();
	
}