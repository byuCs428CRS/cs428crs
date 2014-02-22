package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: Nick Humrich
 * @date: 2/21/14
 */
@ResponseStatus( value = HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends RuntimeException {

	public NotAuthorizedException() {
		super();
	}

	public NotAuthorizedException(String message) {
		super(message);
	}

	public NotAuthorizedException(String message, Exception cause) {
		super(message, cause);
	}

}
