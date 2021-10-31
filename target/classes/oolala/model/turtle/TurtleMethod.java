package oolala.model.turtle;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a parsed turtle method
 *
 * @author Nolan Burroughs
 */
public class TurtleMethod {

  private final String methodName;
  private final List<Object> parameters;

  /**
   * Constructs a turtle method object with no parameters
   *
   * @param methodName The name of the method
   */
  public TurtleMethod(String methodName) {
    this.methodName = methodName;
    parameters = new ArrayList<>();
  }

  /**
   * Constructs a turtle method object with parameters
   *
   * @param methodName The name of the method
   * @param parameters The parameters
   */
  public TurtleMethod(String methodName, List<Object> parameters) {
    this.methodName = methodName;
    this.parameters = parameters;
  }

  /**
   * @return Method Name
   */
  public String getMethodName() {
    return methodName;
  }

  /**
   * @return Parameters
   */
  public List<Object> getParameters() {
    return parameters;
  }

  /**
   * Makes what the original turtle command string would be
   *
   * @return Command string
   */
  @Override
  public String toString() {
    String displayString = methodName;
    for (Object i : parameters) {
      displayString = String.format("%s %s", displayString, i.toString());
    }
    return displayString;
  }
}
