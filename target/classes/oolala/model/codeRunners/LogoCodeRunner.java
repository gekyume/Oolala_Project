package oolala.model.codeRunners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import oolala.model.turtle.LogoTurtleModel;
import oolala.model.turtle.TurtleMethod;
import oolala.model.turtle.TurtleModel;

/**
 * This class implements the commands for Logo
 *
 * @author Nolan Burroughs
 */
public class LogoCodeRunner extends CodeRunner {

  private int currentCodeLine;
  private List<TurtleMethod> myCode;

  /**
   * Constructs a code runner object
   */
  public LogoCodeRunner() {
    super();
    currentCodeLine = 0;
    myCode = new ArrayList<>();
    myCurrentTurtles = new ArrayList<>();
    LogoTurtleModel turtle = new LogoTurtleModel(home);
    myCurrentTurtles.add(turtle);
    myTurtles = new HashMap<>();
    myTurtles.put(1, turtle);
  }

  /**
   * @param code Add the given code to the codebase
   */
  @Override
  public void addCode(List<TurtleMethod> code) {
    myCode.addAll(code);
  }

  /**
   * Clears the currently stored code and sets current line back to zero.
   */
  @Override
  public void clearCode() {
    myCode = new ArrayList<>();
    currentCodeLine = 0;
  }

  // Returns if every line has been run
  @Override
  protected boolean isDoneRunning() {
    return currentCodeLine == myCode.size();
  }

  // Fetches the line of code at the current index
  @Override
  protected TurtleMethod getCurrentMethod() {
    return myCode.get(currentCodeLine);
  }

  /**
   * Moves the current turtles forward
   *
   * @param distance Distance to move
   */
  protected void FD(List<Integer> distance) {
    List<TurtleModel> turtles = (List<TurtleModel>) myCurrentTurtles;
    for (TurtleModel turtle : turtles) {
      turtle.move(distance.get(0));
    }
    currentCodeLine++;
  }

  /**
   * Moves the current turtles back
   *
   * @param distance Distance to move
   */
  protected void BK(List<Integer> distance) {
    List<TurtleModel> turtles = (List<TurtleModel>) myCurrentTurtles;
    for (TurtleModel turtle : turtles) {
      turtle.move(-distance.get(0));
    }
    currentCodeLine++;
  }

  /**
   * Rotates the current turtles left
   *
   * @param degrees Degrees to turn
   */
  protected void LT(List<Integer> degrees) {
    for (TurtleModel turtle : myCurrentTurtles) {
      turtle.rotate(degrees.get(0));
    }
    currentCodeLine++;
  }

  /**
   * Rotates the current turtles right
   *
   * @param degrees Degrees to turn
   */
  protected void RT(List<Integer> degrees) {
    for (TurtleModel turtle : myCurrentTurtles) {
      turtle.rotate(-degrees.get(0));
    }
    currentCodeLine++;
  }

  /**
   * Sets the current turtles to have pens up
   *
   * @param empty Empty list for ease of reflection
   */
  protected void PU(List<Integer> empty) {
    for (TurtleModel turtle : myCurrentTurtles) {
      turtle.setPenState(false);
    }
    currentCodeLine++;
  }

  /**
   * Sets the current turtles to have pens down
   *
   * @param empty Empty list for ease of reflection
   */
  protected void PD(List<Integer> empty) {
    for (TurtleModel turtle : myCurrentTurtles) {
      turtle.setPenState(true);
    }
    currentCodeLine++;
  }

  /**
   * Tells the current turtles to stamp image on the screen
   *
   * @param empty Empty list for ease of reflection
   */
  protected void STAMP(List<Integer> empty) {
    for (TurtleModel turtle : myCurrentTurtles) {
      LogoTurtleModel logoTurtle = (LogoTurtleModel) turtle;
      logoTurtle.stamp();
    }
    currentCodeLine++;
  }

  /**
   * Tells the current turtles to move to home
   *
   * @param empty Empty list for ease of reflection
   */
  protected void HOME(List<Integer> empty) {
    for (TurtleModel turtle : myCurrentTurtles) {
      LogoTurtleModel logoTurtle = (LogoTurtleModel) turtle;
      logoTurtle.home();
    }
    currentCodeLine++;
  }

  /**
   * Tells the current turtles to show their image
   *
   * @param empty Empty list for ease of reflection
   */
  protected void ST(List<Integer> empty) {
    for (TurtleModel turtle : myCurrentTurtles) {
      LogoTurtleModel logoTurtle = (LogoTurtleModel) turtle;
      logoTurtle.setVisible(true);
    }
    currentCodeLine++;
  }

  /**
   * Tells current turtles to hide their image
   *
   * @param empty Empty list for ease of reflection
   */
  protected void HT(List<Integer> empty) {
    for (TurtleModel turtle : myCurrentTurtles) {
      LogoTurtleModel logoTurtle = (LogoTurtleModel) turtle;
      logoTurtle.setVisible(false);
    }
    currentCodeLine++;
  }

  /**
   * Sets specific turtles as current turtles, creating them if they do not already exist
   *
   * @param turtles Turtles to make current turtles
   */
  protected void TELL(List<Integer> turtles) {
    myCurrentTurtles = new ArrayList<>();
    for (int i : turtles) {
      if (myTurtles.containsKey(i)) {
        myCurrentTurtles.add(myTurtles.get(i));
      } else {
        LogoTurtleModel newTurtle = new LogoTurtleModel(home);
        myTurtles.put(i, newTurtle);
        myCurrentTurtles.add(newTurtle);
        myTurtlesBean.setProperty(newTurtle);
      }
    }
    currentCodeLine++;
  }
}
