package oolala.model.parsers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import oolala.model.exceptions.IncorrectCommand;
import oolala.model.turtle.TurtleMethod;

/**
 * Parser class that parses the given inputs to get the apprioate commands
 *
 * @author Kyle White
 */
public abstract class Parser {

  private static final String DEFAULT_COMMAND_RESOURCES_PACKAGE = "oolala.model.resources.Commands";
  private static final String DEFAULT_ERROR_RESOURCES_PACKAGE = "oolala.model.resources.Errors";
  protected final ResourceBundle myCommandResources;
  protected final ResourceBundle myErrorResources;
  protected final Set<String> commandsWithArguments;
  protected final Set<String> commandsWithoutArguments;

  /**
   * Constructor class that initializes command sets and establishes resource bundle. Calls
   * addCommands to fill sets with appreciate commands
   *
   * @param language Language for the resources file
   */
  public Parser(String language, String application) {
    commandsWithArguments = new HashSet<>();
    commandsWithoutArguments = new HashSet<>();
    myCommandResources = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_COMMAND_RESOURCES_PACKAGE, application));
    myErrorResources = ResourceBundle.getBundle(
        String.format("%s%s", DEFAULT_ERROR_RESOURCES_PACKAGE, language));
    addCommands();
  }

  /**
   * @return commandsWithArguments
   */
  public Set<String> getCommandsWithArguments() {
    return commandsWithArguments;
  }

  /**
   * @return commandsWithoutArguments
   */
  public Set<String> getCommandsWithoutArguments() {
    return commandsWithoutArguments;
  }

  /**
   * @return myResources
   */
  public ResourceBundle getMyCommandResources() {
    return myCommandResources;
  }

  // Fills the sets up with commands from resource file
  private void addCommands() {
    commandsWithArguments.addAll(
        List.of(myCommandResources.getString("CommandsWithArguments").split(" ")));
    commandsWithoutArguments.addAll(
        List.of(myCommandResources.getString("CommandsWithoutArguments").split(" ")));
  }

  /**
   * Parses line of commands
   *
   * @param line of Commands
   * @return List of commands
   */
  public List<TurtleMethod> parseLine(String line) {
    ArrayList<TurtleMethod> ret = new ArrayList<>();
    String[] words = line.split("[\s\t]+");
    if (words.length == 0 || words[0].equals("#")) {
      return ret;
    }
    for (int i = 0; i < words.length; i++) {
      if (words[i].length() == 0) {
        continue;
      }
      String command = words[i].toUpperCase(Locale.ROOT);
      if (commandsWithoutArguments.contains(command)) {
        ret.add(new TurtleMethod(command));
      } else if (commandsWithArguments.contains(command)) {
        List<Object> parameters = checkForArgument(words, i);
        ret.add(new TurtleMethod(command, parameters));
        i += parameters.size();
      } else {
        throw new IncorrectCommand(myErrorResources.getString("IncorrectCommand"), words[i]);
      }
    }
    return ret;
  }

  protected abstract List<Object> checkForArgument(String[] words, int i);

  /**
   * Checks to see if command argument is valid
   *
   * @param arg Command argument
   * @return true/false
   */
  public Boolean checkCommandArgument(String arg) {
    int check;
    try {
      check = Integer.parseInt(arg);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return (check >= 0);
  }

}
