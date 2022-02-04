package me.tbandawa.notesapi.exception;

import java.time.ZoneOffset;
import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    		Exception exception, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest webRequest) {
    	ErrorResponse errorResponse = new ErrorResponse.ErrorResponseBuilder()
                .withStatus(httpStatus)
                .withDetail(exception.getLocalizedMessage())
                .withMessage("Something went wrong")
                .withErrorCode("502")
                .withStatus(HttpStatus.BAD_GATEWAY)
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

}