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
    public ResponseEntity<ErrorResponse> noteNotFound(NoteNotFoundException ex, WebRequest request){
        ErrorResponse errorResponse =new ErrorResponse.ErrorResponseBuilder()
        		.withDetail("Not able to find customer record")
                .withMessage("Not a valid user id.Please provide a valid user id or contact system admin.")
                .withErrorCode("404")
                .withStatus(HttpStatus.NOT_FOUND)
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(NoteServiceException.class)
    protected ResponseEntity<Object> handleCustomAPIException(NoteServiceException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ErrorResponse errorResponse =new ErrorResponse.ErrorResponseBuilder()
                .withStatus(status)
                .withDetail("custom exception")
                .withMessage(ex.getLocalizedMessage())
                .withErrorCode("503")
                .withStatus(status.SERVICE_UNAVAILABLE)
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleCustomAPIException(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ErrorResponse errorResponse =new ErrorResponse.ErrorResponseBuilder()
                .withStatus(status)
                .withDetail("Something went wrong")
                .withMessage(ex.getLocalizedMessage())
                .withErrorCode("502")
                .withStatus(status.BAD_GATEWAY)
                .withTimeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

}