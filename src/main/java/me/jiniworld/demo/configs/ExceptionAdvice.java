package me.jiniworld.demo.configs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import me.jiniworld.demo.exception.AuthorizationHeaderNotExistsException;
import me.jiniworld.demo.exception.TokenExpiredException;
import me.jiniworld.demo.models.responses.ErrorResponse;

@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(TokenExpiredException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(TokenExpiredException e) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
	    final ErrorResponse response = new ErrorResponse(status.value() +"", e.getMessage());
	    return ResponseEntity.status(status).body(response);
	}
	
	@ExceptionHandler(AuthorizationHeaderNotExistsException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(AuthorizationHeaderNotExistsException e) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
	    final ErrorResponse response = new ErrorResponse(status.value() +"", e.getMessage());
	    return ResponseEntity.status(status).body(response);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorResponse response = new ErrorResponse(status.value() +"", e.getMessage());
        return ResponseEntity.status(status).body(response);
    }
	
	@ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = new ErrorResponse(status.value() +"", e.toString());
        return ResponseEntity.status(status).body(response);
    }
}