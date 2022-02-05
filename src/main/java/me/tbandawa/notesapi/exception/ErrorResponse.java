package me.tbandawa.notesapi.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
	
    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
    private String[] messages;
    private String detail;
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

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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
        private String[] messages;
        private String detail;
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
        
        public ErrorResponseBuilder withMessages(String[] messages) {
        	this.messages = messages;
        	return this;
        }
    	
        public ErrorResponseBuilder withDetail(String detail) {
        	this.detail = detail;
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
        	errorResponse.messages = this.messages;
        	errorResponse.detail = this.detail;
        	errorResponse.timeStamp = this.timeStamp;
        	return errorResponse;
        	
        }
        
    }

}
