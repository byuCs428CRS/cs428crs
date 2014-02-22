package exceptions;

/**
 * @author: Nick Humrich
 * @date: 2/21/14
 */
public class AccountAlreadyExistsException extends ConflictException {

	public AccountAlreadyExistsException() {
		super();
	}

	public AccountAlreadyExistsException(String message) {
		super(message);
	}

	public AccountAlreadyExistsException(String message, Exception cause) {
		super(message, cause);
	}
}
