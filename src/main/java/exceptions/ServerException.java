package exceptions;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public class ServerException extends Exception {

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
