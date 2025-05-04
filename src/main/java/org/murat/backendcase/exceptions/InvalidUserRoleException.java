package org.murat.backendcase.exceptions;

public class InvalidUserRoleException extends RuntimeException {
	private static final long serialVersionUID = -8241557357546854901L;

	public InvalidUserRoleException(String message) {
		super(message);
	}
}