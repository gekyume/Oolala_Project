package oolala.model.OolalaModels;

import oolala.model.codeRunners.LogoCodeRunner;
import oolala.model.parsers.LogoParser;

/**
 * OolalaModel class for the logo application. It extends the abstract model class and initializes
 * the correct parser and code runner.
 *
 * @author Kyle White
 */
public class OolalaModelLogo extends OolalaModel {

  /**
   * Constructor class that creates a linked list for the history and creates a parser object for
   * the given language
   *
   * @param language Language to choose which resource file to use for the parser
   */
  public OolalaModelLogo(String language) {
    super(language);
  }

  // Adds the parser and code runner specific to the application
  protected void addParserAndCodeRunner(String language) {
    myParser = new LogoParser(language);
    myCodeRunner = new LogoCodeRunner();
  }

}
