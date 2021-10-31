package oolala.model.OolalaModels;

import oolala.model.codeRunners.DarwinCodeRunner;
import oolala.model.parsers.DarwinParser;

/**
 * OolalaModel class for the Darwin application. It extends the abstract model class and initializes
 * the correct parser and code runner.
 *
 * @author Kyle White
 */
public class OolalaModelDarwin extends OolalaModel {

  /**
   * Constructor class that creates a linked list for the history and creates a parser object for
   * the given language
   *
   * @param language Language to choose which resource file to use for the parser
   */
  public OolalaModelDarwin(String language) {
    super(language);
  }

  // Adds the parser and code runner specific to the application
  protected void addParserAndCodeRunner(String language) {
    myParser = new DarwinParser(language);
    myCodeRunner = new DarwinCodeRunner(language);
  }

}
