package oolala.model.OolalaModels;

import java.util.LinkedList;
import java.util.Queue;
import oolala.model.codeRunners.LogoCodeRunner;
import oolala.model.parsers.LSystemParser;
import oolala.model.turtle.TurtleMethod;

/**
 * OolalaModel class for the LSystem application. It extends the abstract model class and
 * initializes the correct parser and code runner. It also overrides the parseBlock method.
 *
 * @author Kyle White
 */
public class OolalaModelLSystem extends OolalaModel {

  private LSystemParser Parser;
  private int myLoops;

  /**
   * Constructor class that creates a linked list for the history and creates a parser object for
   * the given language
   *
   * @param language Language to choose which resource file to use for the parser
   */
  public OolalaModelLSystem(String language) {
    super(language);
    myLoops = 3;
  }

  // Adds the parser and code runner specific to the application
  protected void addParserAndCodeRunner(String language) {
    int defaultAngle = 30;
    int defaultLength = 10;
    Parser = new LSystemParser(language, defaultAngle, defaultLength);
    myCodeRunner = new LogoCodeRunner();
  }

  /**
   * Method takes in a block, resets parser, calls parseLine for each line in the block, and checks
   * for unknown characters in block. It will then get the turtle commands and pass those off to the
   * code runner and update the history.
   *
   * @param block block of input
   * @return Queue of commands
   */
  @Override
  public Queue<TurtleMethod> parseBlock(String block) {
    LinkedList<TurtleMethod> queue;
    String[] lines = block.split("\\r?\\n");
    Parser.resetParser();
    for (String line : lines) {
      Parser.parseLine(line);
    }
    Parser.checkForUnknownCharacters();
    queue = Parser.getLoop(myLoops);
    updateHistory(queue);
    myCodeRunner.addCode(queue);
    return queue;
  }

  /**
   * @param loops number of loops
   */
  public void setLoops(int loops) {
    myLoops = loops;
  }

  /**
   * @param distance Sets the distance for movements
   */
  public void setDistance(int distance) {
    Parser.setLength(distance);
  }

  /**
   * @param degree Sets the degree of turns
   */
  public void setDegree(int degree) {
    Parser.setAngle(degree);
  }
}
