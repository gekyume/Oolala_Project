package oolala.model.codeRunners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import oolala.model.exceptions.IncorrectCommand;
import oolala.model.turtle.DarwinTurtleModel;
import oolala.model.turtle.TurtleMethod;
import oolala.model.turtle.TurtleModel;

/**
 * This class represents a code runner for Darwin simulators.  It expects each species code to be
 * inputted in order and once per species.  It assumes that the available spaces goes from 50 to 600
 * horizontally and 50 to 550 vertically.  It also assumes that turtles cover a 1 by 1 square in the
 * center of their spot.
 *
 * @author Nolan Burroughs
 */
public class DarwinCodeRunner extends CodeRunner {

  public static final int LEFT_WALL = 50;
  public static final int RIGHT_WALL = 600;
  public static final int TOP_WALL = 50;
  public static final int BOTTOM_WALL = 550;
  private static final String DEFAULT_ERROR_RESOURCES_PACKAGE = "oolala.model.resources.Errors";
  private int myCurrentTurtleIndex;
  private int myCurrentTurtleNumber;
  private int myRadius;
  private TurtleModel myCurrentTurtle;
  private List<Integer> myTurtleNumbers;
  private Map<Integer, Integer> myTurtleCurrentCodeLine;
  private List<List<TurtleMethod>> myCode;
  private Random random;
  private ResourceBundle myErrorResources;

  /**
   * Constructs a Darwin code runner that starts at zero, has a default radius of five, and has a
   * java Random for random generation.
   */
  public DarwinCodeRunner(String language) {
    super();
    myCurrentTurtleIndex = 0;
    myCurrentTurtleNumber = 0;
    myTurtleNumbers = new ArrayList<>();
    myCurrentTurtles = new ArrayList<>();
    myTurtleCurrentCodeLine = new HashMap<>();
    random = new Random();
    myCode = new ArrayList<>();
    myRadius = 5;
    myErrorResources = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_ERROR_RESOURCES_PACKAGE, language));
  }

  /**
   * Adds each species code in order as a whole block.
   *
   * @param code Add the given code to the codebase
   */
  @Override
  public void addCode(List<TurtleMethod> code) {
    myCode.add(code);
  }

  /**
   * Add a new Darwin turtle model at a given location with a starting species.
   *
   * @param location Starting location
   * @param species  Starting species
   */
  public void addTurtle(double[] location, int species) {
    int turtleNumber = myTurtleNumbers.size();
    myTurtleNumbers.add(turtleNumber);
    myTurtleCurrentCodeLine.put(turtleNumber, 0);
    TurtleModel newTurtle = new DarwinTurtleModel(location, species);
    myCurrentTurtles.add(newTurtle);
    myTurtlesBean.setProperty(newTurtle);
  }

  /**
   * @param radius New radius to set current radius
   */
  public void setRadius(int radius) {
    myRadius = radius;
  }

  /**
   * Clears the currently inputted code and sets all the turtles in the environment back to line
   * zero.
   */
  @Override
  public void clearCode() {
    myCode = new ArrayList<>();
    for (Integer turtle : myTurtleCurrentCodeLine.keySet()) {
      myTurtleCurrentCodeLine.put(turtle, 0);
    }
  }

  /**
   * @return If there are no turtles created
   */
  @Override
  protected boolean isDoneRunning() {
    return myCurrentTurtles.size() == 0;
  }

  /**
   * Cycles through the possible turtles once in a random order.  If back at the beginning, shuffles
   * the turtles again.  Returns the code line for the current turtle based on the code for its
   * current species.
   *
   * @return Code method to run
   * @throws IncorrectCommand No commands entered for that species
   * @throws IncorrectCommand Species code does not loop
   */
  @Override
  protected TurtleMethod getCurrentMethod() {
    if (myCurrentTurtleIndex == myTurtleNumbers.size()) {
      myCurrentTurtleIndex = 0;
      Collections.shuffle(myTurtleNumbers);
    } else if (myCurrentTurtleIndex == 0) {
      Collections.shuffle(myTurtleNumbers);
    }
    myCurrentTurtleNumber = myTurtleNumbers.get(myCurrentTurtleIndex);
    myCurrentTurtle = myCurrentTurtles.get(myCurrentTurtleNumber);
    DarwinTurtleModel turtle = (DarwinTurtleModel) myCurrentTurtle;
    if (myCode.size() <= turtle.getSpecies() || myCode.get(turtle.getSpecies()).size() == 0) {
      myCode = new ArrayList<>();
      throw new IncorrectCommand(
          String.format(myErrorResources.getString("SpeciesMissingCode"), turtle.getSpecies()));
    } else if (myCode.get(turtle.getSpecies()).size() <= myTurtleCurrentCodeLine.get(
        myCurrentTurtleNumber)) {
      myCode = new ArrayList<>();
      throw new IncorrectCommand(
          String.format(myErrorResources.getString("SpeciesNoLoop"), turtle.getSpecies()));
    }
    return myCode.get(turtle.getSpecies())
        .get(myTurtleCurrentCodeLine.get(myCurrentTurtleNumber));
  }

  /**
   * Moves the current turtle.  Moves back if it lands on another turtle or goes past the map.
   *
   * @param distance Distance to move
   */
  protected void MOVE(List<Integer> distance) {
    myCurrentTurtle.move(distance.get(0));
    if (wall(0) || getAdjacentTurtles(0).size() > 0) {
      myCurrentTurtle.move(-distance.get(0));
    }
    incrementCode();
  }

  /**
   * Turns the current turtle left.
   *
   * @param degree Degrees to turn
   */
  protected void LEFT(List<Integer> degree) {
    myCurrentTurtle.rotate(degree.get(0));
    incrementCode();
  }

  /**
   * Turns the current turtle right.
   *
   * @param degree Degrees to turn
   */
  protected void RIGHT(List<Integer> degree) {
    myCurrentTurtle.rotate(-degree.get(0));
    incrementCode();
  }

  /**
   * Goes to the given line if nothing within radius, increments to the next line otherwise.
   *
   * @param codeLine Code line to jump to
   */
  protected void IFEMPTY(List<Integer> codeLine) {
    if (getAdjacentTurtles(myRadius).isEmpty() && !wall(myRadius)) {
      myTurtleCurrentCodeLine.put(myCurrentTurtleNumber, codeLine.get(0) - 1);
      return;
    }
    incrementCodeLine();
  }

  /**
   * Goes to the given line if wall within radius, increments to the next line otherwise.
   *
   * @param codeLine Code line to jump to
   */
  protected void IFWALL(List<Integer> codeLine) {
    if (wall(myRadius)) {
      myTurtleCurrentCodeLine.put(myCurrentTurtleNumber, codeLine.get(0) - 1);
      return;
    }
    incrementCodeLine();
  }

  /**
   * Goes to the given line if ally within radius, increments to the next line otherwise.
   *
   * @param codeLine Code line to jump to
   */
  protected void IFSAME(List<Integer> codeLine) {
    DarwinTurtleModel myTurtle = (DarwinTurtleModel) myCurrentTurtle;
    for (int i : getAdjacentTurtles(myRadius)) {
      DarwinTurtleModel turtle = (DarwinTurtleModel) myCurrentTurtles.get(i);
      if (turtle.getSpecies() == myTurtle.getSpecies()) {
        myTurtleCurrentCodeLine.put(myCurrentTurtleNumber, codeLine.get(0) - 1);
        return;
      }
    }
    incrementCodeLine();
  }

  /**
   * Goes to the given line if enemy within radius, increments ot the next line otherwise.
   *
   * @param codeLine Code line to jump to
   */
  protected void IFENEMY(List<Integer> codeLine) {
    DarwinTurtleModel myTurtle = (DarwinTurtleModel) myCurrentTurtle;
    for (int i : getAdjacentTurtles(myRadius)) {
      DarwinTurtleModel turtle = (DarwinTurtleModel) myCurrentTurtles.get(i);
      if (turtle.getSpecies() != myTurtle.getSpecies()) {
        myTurtleCurrentCodeLine.put(myCurrentTurtleNumber, codeLine.get(0) - 1);
        return;
      }
    }
    incrementCodeLine();
  }

  /**
   * Goes to the given code line or increments at random.
   *
   * @param codeLine Code line to jump to
   */
  protected void IFRANDOM(List<Integer> codeLine) {
    if (random.nextBoolean()) {
      myTurtleCurrentCodeLine.put(myCurrentTurtleNumber, codeLine.get(0) - 1);
    }
    incrementCodeLine();
  }

  /**
   * Jumps to the given code line.
   *
   * @param codeLine Code line to jump to
   */
  protected void GO(List<Integer> codeLine) {
    myTurtleCurrentCodeLine.put(myCurrentTurtleNumber, codeLine.get(0) - 1);
  }

  /**
   * Infects all adjacent enemies and sets their new code line to zero.
   *
   * @param empty Empty list for ease of reflection
   */
  protected void INFECT(List<Integer> empty) {
    DarwinTurtleModel myTurtle = (DarwinTurtleModel) myCurrentTurtle;
    for (int i : getAdjacentTurtles(myRadius)) {
      DarwinTurtleModel turtle = (DarwinTurtleModel) myCurrentTurtles.get(i);
      if (turtle.getSpecies() != myTurtle.getSpecies()) {
        turtle.setSpecies(myTurtle.getSpecies());
        myTurtleCurrentCodeLine.put(i, 0);
      }
    }
    incrementCode();
  }

  /**
   * Returns list of turtles that are within a given radius of myCurrentTurtle
   *
   * @param radius Radius to check for turtles
   * @return List of adjacent turtle numbers
   */
  private List<Integer> getAdjacentTurtles(int radius) {
    List<Integer> adjacent = new ArrayList<>();
    for (int i = 0; i < myCurrentTurtles.size(); i++) {
      if (isAdjacent(myCurrentTurtles.get(i), radius) && i != myCurrentTurtleNumber) {
        adjacent.add(i);
      }
    }
    return adjacent;
  }

  /**
   * Returns true is current turtle is adjacent to and looking at given turtle
   *
   * @param turtle Turtle to check for adjacency
   * @param radius Max distance from current turtle to given turtle
   * @return If moving the current turtle up to the given distance lands on the given turtle
   */
  private boolean isAdjacent(TurtleModel turtle, int radius) {
    for (int i = 0; i <= radius; i++) {
      myCurrentTurtle.move(i);
      if (myCurrentTurtle.getX() == turtle.getX() && myCurrentTurtle.getY() == turtle.getY()) {
        myCurrentTurtle.move(-i);
        return true;
      }
      myCurrentTurtle.move(-i);
    }
    return false;
  }

  /**
   * Returns true if current turtle is adjacent to and looking at a wall
   *
   * @param radius Distance to look for a wall
   * @return If moving the radius distance moves past a wall
   */
  private boolean wall(int radius) {
    myCurrentTurtle.move(radius);
    boolean pastWall = myCurrentTurtle.getX() <= LEFT_WALL
        || myCurrentTurtle.getX() >= RIGHT_WALL
        || myCurrentTurtle.getY() <= TOP_WALL
        || myCurrentTurtle.getY() >= BOTTOM_WALL;
    myCurrentTurtle.move(-radius);
    return pastWall;
  }

  /**
   * Increments the CodeLine, and increments the currentTurtleIndex
   */
  private void incrementCode() {
    incrementCodeLine();
    myCurrentTurtleIndex++;
  }

  /**
   * Increments just the current turtle code line
   */
  private void incrementCodeLine() {
    myTurtleCurrentCodeLine.put(myCurrentTurtleNumber,
        myTurtleCurrentCodeLine.get(myCurrentTurtleNumber) + 1);
  }
}
