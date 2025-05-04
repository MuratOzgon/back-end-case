package org.murat.backendcase.exceptions;

import org.murat.backendcase.exception.CustomerNotFoundException;
import org.murat.backendcase.exceptions.InvalidUserRoleException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Map<String, String>> handleResponseStatus(ResponseStatusException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getReason());
		return ResponseEntity.status(ex.getStatusCode()).body(error);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleCustomerNotFound(CustomerNotFoundException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(InvalidUserRoleException.class)
	public ResponseEntity<Map<String, String>> handleInvalidUserRole(InvalidUserRoleException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleGenericException(Exception exception) {
		exception.printStackTrace();
		ProblemDetail errorDetail;

		if (exception instanceof BadCredentialsException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
			errorDetail.setProperty("description", "The username or password is incorrect");
			return errorDetail;
		} else if (exception instanceof AccountStatusException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
			errorDetail.setProperty("description", "The account is locked");
			return errorDetail;
		} else if (exception instanceof AccessDeniedException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
			errorDetail.setProperty("description", "You are not authorized to access this resource");
			return errorDetail;
		} else if (exception instanceof SignatureException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
			errorDetail.setProperty("description", "The JWT signature is invalid");
			return errorDetail;
		} else if (exception instanceof ExpiredJwtException) {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
			errorDetail.setProperty("description", "The JWT token has expired");
			return errorDetail;
		} else {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
			errorDetail.setProperty("description", "An unexpected error occurred");
			return errorDetail;
		}
	}
}
