package oolala.model.codeRunners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oolala.model.turtle.TurtleMethod;
import oolala.model.turtle.TurtleModel;
import oolala.util.Bean;

/**
 * Abstract class representing the controller for running a code in a given language.  The methods
 * will implement the commands for a language with shared methods being implemented in this abstract
 * class.
 *
 * @author Nolan Burroughs
 * @author Casey Goldstein
 */
public abstract class CodeRunner {

  protected double[] home;
  protected Bean myTurtlesBean;
  protected List<TurtleModel> myCurrentTurtles;
  protected Map<Integer, TurtleModel> myTurtles;

  /**
   * Constructs a code runner that starts at line 0, with an empty set of code, and an empty array
   * of turtles
   */
  public CodeRunner() {
    myTurtles = new HashMap<>();
    home = new double[]{0, 0};
    myTurtlesBean = new Bean("Turtles");
  }

  /**
   * Runs one line of code by calling the method at the current line of code by using reflection to
   * call its method.
   *
   * @return True if it is at the end of the code, false otherwise
   * @throws NoSuchMethodException     Should never happen, due to method name not being
   *                                   implemented
   * @throws InvocationTargetException Should never happen, method not existing
   * @throws IllegalAccessException    Should never happen, trying to access a private method
   */
  public boolean runLine()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    if (isDoneRunning()) {
      return true;
    }
    TurtleMethod currentMethod = getCurrentMethod();
    runLine(currentMethod);
    return false;
  }

  public void runLine(TurtleMethod currentMethod)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Class[] carg = new Class[1];
    carg[0] = List.class;
    Method method = this.getClass()
        .getDeclaredMethod(currentMethod.getMethodName(), carg);
    method.invoke(this, currentMethod.getParameters());
  }

  /**
   * @param code Add the given code to the codebase
   */
  public abstract void addCode(List<TurtleMethod> code);

  /**
   * @return Currently running turtles
   */
  public List<TurtleModel> getTurtles() {
    return myCurrentTurtles;
  }

  /**
   * @return Created turtle bean
   */
  public Bean getBean() {
    return myTurtlesBean;
  }

  /**
   * Set own home and the home of all the turtle models to the new home
   *
   * @param newHome Inputted new home
   */
  public void setHome(double[] newHome) {
    home = newHome;
    for (Integer i : myTurtles.keySet()) {
      TurtleModel turtle = myTurtles.get(i);
      turtle.setHome(home);
    }
  }

  /**
   * @return Current home of the turtles
   */
  public double[] getHome() {
    return home;
  }

  /**
   * Clears the currently inputted code.
   */
  public abstract void clearCode();

  // Abstract method for if the code runner is done running
  protected abstract boolean isDoneRunning();

  // Abstract method to fetch the current method to run
  protected abstract TurtleMethod getCurrentMethod();
}
