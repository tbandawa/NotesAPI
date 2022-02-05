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
        		.withDetail(noteNotFoundException.getLocalizedMessage())
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
                .withStatus(status)
                .withDetail(noteNotFoundException.getLocalizedMessage())
                .withMessage("Note Service Exception")
                .withErrorCode("503")
                .withStatus(status.SERVICE_UNAVAILABLE)
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleCustomAPIException(
    		Exception exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ErrorResponse errorResponse = new ErrorResponse.ErrorResponseBuilder()
                .withStatus(status)
                .withMessage("Something went wrong")
                .withDetail(exception.getLocalizedMessage())
                .withErrorCode("502")
                .withStatus(HttpStatus.BAD_GATEWAY)
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
    		MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	
    	List<String> validationErrors = new ArrayList<>();
        for(ObjectError objectError : ex.getBindingResult().getAllErrors()) {
        	validationErrors.add(objectError.getDefaultMessage().toString());
        }
  
    	ErrorResponse errorResponse = new ErrorResponse.ErrorResponseBuilder()
                .withStatus(status)
                .withMessage("Invalid Inputs")
                .withDetails(validationErrors)
                .withErrorCode("400")
                .withStatus(status.BAD_REQUEST)
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    	return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

}