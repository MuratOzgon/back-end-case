package org.murat.backendcase.exception;

public class CustomerNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4139648441948256177L;

	public CustomerNotFoundException(Long customerId) {
		super("Customer with ID " + customerId + " not found");
	}
}