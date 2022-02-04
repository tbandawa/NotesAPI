package me.tbandawa.notesapi.exception;

public class NoteNotFoundException extends Exception {
	
	public NoteNotFoundException() {
		super();
	}
	
	public NoteNotFoundException(String message) {
		super(message);
	}
	
	public NoteNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
