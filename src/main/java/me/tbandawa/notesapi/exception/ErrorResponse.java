package me.tbandawa.notesapi.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	
    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
    private List<String> details;
    private LocalDateTime timeStamp;
        
    public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public static final class ErrorResponseBuilder {
    	
        private HttpStatus httpStatus;
        private String errorCode;
        private String message;
        private List<String> details;
        private LocalDateTime timeStamp;
        
        public ErrorResponseBuilder() {}
        
        public ErrorResponseBuilder errorResponseBuilder() {
        	return new ErrorResponseBuilder();
        }
        
        public ErrorResponseBuilder withStatus(HttpStatus httpStatus) {
        	this.httpStatus = httpStatus;
        	return this;
        }
        
        public ErrorResponseBuilder withErrorCode(String errorCode) {
        	this.errorCode = errorCode;
        	return this;
        }
        
        public ErrorResponseBuilder withMessage(String message) {
        	this.message = message;
        	return this;
        }
    	
        public ErrorResponseBuilder withDetails(List<String> details) {
        	this.details = details;
        	return this;
        }
        
        public ErrorResponseBuilder withTimeStamp(LocalDateTime timeStamp) {
        	this.timeStamp = timeStamp;
        	return this;
        }
        
        public ErrorResponse build() {
        	ErrorResponse errorResponse = new ErrorResponse();
        	errorResponse.httpStatus = this.httpStatus;
        	errorResponse.errorCode = this.errorCode;
        	errorResponse.message = this.message;
        	errorResponse.details = this.details;
        	errorResponse.timeStamp = this.timeStamp;
        	return errorResponse;
        	
        }
        
    }

}
