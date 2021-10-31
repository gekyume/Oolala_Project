package oolala.model.parsers;

import java.util.ArrayList;
import java.util.List;
import oolala.model.exceptions.IncorrectCommand;

/**
 * Darwin Parser class that parses the given inputs to get the Logo commands. Extends abstract
 * parser class
 *
 * @author Kyle White
 */
public class DarwinParser extends Parser {

  /**
   * Constructor class that calls the constructor of the super class with Darwin as the application
   * name
   *
   * @param language Current language
   */
  public DarwinParser(String language) {
    super(language, "Darwin");
  }

  // Checks to make sure that there is an argument and calls checkCommandArgument
  protected List<Object> checkForArgument(String[] words, int i) {
    List<Object> ret = new ArrayList<>();
    if (words.length == i + 1) {
      throw new IncorrectCommand(myErrorResources.getString("MissingCommandArgument"),
          words[i]);
    }
    if (!checkCommandArgument(words[i + 1])) {
      throw new IncorrectCommand(myErrorResources.getString("IncorrectCommandArgument"),
          words[i + 1]);
    }
    ret.add(Integer.parseInt(words[i + 1]));
    return ret;
  }

}
