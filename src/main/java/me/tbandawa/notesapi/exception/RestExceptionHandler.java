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
        		.withDetails(noteNotFoundException.getLocalizedMessage())
                .withMessage("Note record not found")
                .withErrorCode("404")
                .withStatus(HttpStatus.NOT_FOUND)
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(NoteServiceException.class)
    protected ResponseEntity<Object> handleCustomAPIException(
    		NoteServiceException noteNotFoundException, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ErrorResponse errorResponse = new ErrorResponse.ErrorResponseBuilder()
                .withStatusCode(status.SERVICE_UNAVAILABLE.value())
                .withDetails(noteNotFoundException.getLocalizedMessage())
                .withMessage("Note Service Exception")
                .withErrorCode("503")
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleCustomAPIException(
    		Exception exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ErrorResponse errorResponse = new ErrorResponse.ErrorResponseBuilder()
                .withStatusCode(HttpStatus.BAD_GATEWAY.value())
                .withErrorCode("502")
                .withMessage("Something went wrong")
                .withDetails(exception.getLocalizedMessage())
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
                .withErrorCode("400")
                .withMessage("Invalid Inputs")
                .withDetails(validationErrors)
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    	return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}