package oolala.model.exceptions;


/**
 * Represents an incorrect command passed into the parser
 *
 * @author Kyle White
 */
public class IncorrectCommand extends RuntimeException {

  /**
   * Create an exception based on an issue in our code.
   */
  public IncorrectCommand(String message, Object... values) {
    super(String.format(message, values));
  }

  /**
   * Create exception based on a caught exception with a different message.
   */
  public IncorrectCommand(Throwable cause, String message, Object... values) {
    super(String.format(message, values), cause);
  }

  /**
   * Create exception based on a caught exception, with no additional message.
   */
  public IncorrectCommand(Throwable cause) {
    super(cause);
  }
}
