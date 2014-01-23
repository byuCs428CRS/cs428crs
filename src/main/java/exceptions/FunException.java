package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
public class FunException extends RuntimeException {
  public FunException() {
    super();
  }

  public FunException(String message) {
    super(message);
  }
}
