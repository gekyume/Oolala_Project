package oolala.model.OolalaModels;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import oolala.model.parsers.Parser;
import oolala.model.turtle.TurtleMethod;
import oolala.model.codeRunners.CodeRunner;

/**
 * Model Class for OOLALA that handles the backend logic and controls the parsing and storage of the
 * commands
 *
 * @author Kyle White
 */
public abstract class OolalaModel {

  protected List<TurtleMethod> myHistory;
  protected Parser myParser;
  protected CodeRunner myCodeRunner;

  /**
   * Constructor class that creates a linked list for the history and creates a parser object for
   * the given language
   *
   * @param language Language to choose which resource file to use for the parser
   */
  public OolalaModel(String language) {
    myHistory = new ArrayList<>();
    addParserAndCodeRunner(language);
  }

  // Adds the parser and code runner specific to the application
  protected abstract void addParserAndCodeRunner(String language);

  /**
   * Method takes in a block and calls parseLine for each line in the block
   *
   * @param block block of input
   * @return Queue of commands
   */
  public Queue<TurtleMethod> parseBlock(String block) {
    LinkedList<TurtleMethod> queue = new LinkedList<>();
    String[] lines = block.split("\\r?\\n");
    for (String line : lines) {
      queue.addAll(myParser.parseLine(line));
    }
    updateHistory(queue);
    myCodeRunner.addCode(queue);
    return queue;
  }

  /**
   * Updates history to include new lines.
   *
   * @param queue current Queue
   */
  public void updateHistory(Queue<TurtleMethod> queue) {
    myHistory.addAll(queue);
  }

  /**
   * @return CodeRunner
   */
  public CodeRunner getCodeRunner() {
    return myCodeRunner;
  }

  /**
   * @return The current history of turtle methods
   */
  public List<TurtleMethod> getHistory() {
    return myHistory;
  }

}
