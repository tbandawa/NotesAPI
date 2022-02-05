package me.tbandawa.notesapi.exception;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler({NoteNotFoundException.class})
    public ResponseEntity<ErrorResponse> noteNotFound(
    		NoteNotFoundException noteNotFoundException, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse.ErrorResponseBuilder()
        		.withStatusCode(HttpStatus.NOT_FOUND.value())
        		.withDetails(Arrays.asList(noteNotFoundException.getLocalizedMessage()))
                .withMessage("Note record not found")
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(NoteServiceException.class)
    protected ResponseEntity<Object> handleCustomAPIException(
    		NoteServiceException noteNotFoundException, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ErrorResponse errorResponse = new ErrorResponse.ErrorResponseBuilder()
                .withStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .withDetails(Arrays.asList(noteNotFoundException.getLocalizedMessage()))
                .withMessage("Note Service Exception")
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleCustomAPIException(
    		Exception exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ErrorResponse errorResponse = new ErrorResponse.ErrorResponseBuilder()
                .withStatusCode(HttpStatus.BAD_GATEWAY.value())
                .withMessage("Something went wrong")
                .withDetails(Arrays.asList(exception.getLocalizedMessage()))
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_GATEWAY);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
    		MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	
    	List<String> validationErrors = new ArrayList<>();
        for(ObjectError objectError : ex.getBindingResult().getAllErrors()) {
        	validationErrors.add(objectError.getDefaultMessage().toString());
        }
  
    	ErrorResponse errorResponse = new ErrorResponse.ErrorResponseBuilder()
                .withStatusCode(HttpStatus.BAD_REQUEST.value())
                .withMessage("Invalid Inputs")
                .withDetails(validationErrors)
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    	return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}