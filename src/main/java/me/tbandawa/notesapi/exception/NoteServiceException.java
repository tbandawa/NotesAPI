package me.tbandawa.notesapi.exception;

public class NoteServiceException extends Exception {
	
	public NoteServiceException() {
		super();
	}
	
	public NoteServiceException(String message) {
		super(message);
	}
	
	public NoteServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

}