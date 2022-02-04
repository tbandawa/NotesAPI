package me.tbandawa.notesapi.exception;

public class NoteNotFoundException extends Exception {
	
	public NoteNotFoundException() {
		super();
	}
	
	public NoteNotFoundException(String resourceName, String resourceIdentifier, Object fieldValue) {
		super(String.format("%s with %s : %s not found", resourceName, resourceIdentifier, fieldValue));
	}
	
	public NoteNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
