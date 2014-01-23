package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
@ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerException extends RuntimeException {

  public ServerException() {
    super();
  }

  public ServerException(String message) {
    super(message);
  }

  public ServerException(String message, Exception cause) {
    super(message, cause);
  }
}
