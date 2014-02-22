package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: Nick Humrich
 * @date: 2/21/14
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{

	public ConflictException() {
		super();
	}

	public ConflictException(String message) {
		super(message);
	}

	public ConflictException(String message, Exception cause) {
		super(message, cause);
	}
}
