package oolala.model.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import oolala.model.exceptions.IncorrectCommand;

/**
 * Parser class for Logo application
 *
 * @author Kyle White
 */
public class LogoParser extends Parser {

  /**
   * Constructor class that calls the constructor of the super class
   *
   * @param language Language for error messages
   */
  public LogoParser(String language) {
    super(language, "Logo");
  }

  // Checks to make sure that there is an argument, checks to see if the command is TELL, and calls checkCommandArgument
  protected List<Object> checkForArgument(String[] words, int i) {
    List<Object> ret = new ArrayList<>();
    if (words.length == i + 1) {
      throw new IncorrectCommand(myErrorResources.getString("MissingCommandArgument"),
          words[i]);
    }
    if (words[i].toUpperCase(Locale.ROOT).equals("TELL")) {
      ret = getTellArgs(words, i);
      return ret;
    }
    if (!checkCommandArgument(words[i + 1])) {
      throw new IncorrectCommand(myErrorResources.getString("IncorrectCommandArgument"),
          words[i + 1]);
    }
    ret.add(Integer.parseInt(words[i + 1]));
    return ret;
  }

  // Gets arguments for TELL command
  private List<Object> getTellArgs(String[] words, int i) {
    List<Object> ret = new ArrayList<>();
    int iterator = i + 1;
    while (iterator < words.length && checkCommandArgument(words[iterator])) {
      ret.add(Integer.parseInt(words[iterator]));
      iterator++;
    }
    if (iterator == i + 1) {
      throw new IncorrectCommand(myErrorResources.getString("IncorrectCommandArgument"),
          words[i + 1]);
    }
    return ret;
  }
}
