package oolala.model.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import oolala.model.exceptions.IncorrectCommand;
import oolala.model.turtle.TurtleMethod;

/**
 * Parser class for LSystem application
 *
 * @author Kyle White
 */
public class LSystemParser extends Parser {

  private Map<String, List<TurtleMethod>> myConversions;
  private Set<String> myCharacters;
  private Map<String, String> myRules;
  private final Parser logoParser;
  private Boolean hasStart;
  private String START;
  private int myAngle;
  private int myLength;

  /**
   * Constructor class that calls the constructor of the super class sets the language and creates
   * the conversion map
   *
   * @param language Language for error messages
   */
  public LSystemParser(String language, int angle, int length) {
    super(language, "LSystem");
    myConversions = new HashMap<>();
    myCharacters = new HashSet<>();
    myRules = new HashMap<>();
    logoParser = new LogoParser(language);
    hasStart = false;
    START = "";
    myAngle = angle;
    myLength = length;
    addConversions();
  }

  /**
   * @param angle value of angle
   */
  public void setAngle(int angle) {
    myAngle = angle;
  }

  /**
   * @param length value of length
   */
  public void setLength(int length) {
    myLength = length;
  }

  /**
   * @return conversion map
   */
  public Map<String, List<TurtleMethod>> getConversions() {
    return myConversions;
  }

  // Add conversions from resource file to conversion map
  private void addConversions() {
    List<String> commandList = List.of(
        myCommandResources.getString("CommandsWithoutArguments").split(" "));
    List<String> conversionList = List.of(myCommandResources.getString("Conversions").split("/"));
    for (int i = 0; i < commandList.size(); i++) {
      String s = conversionList.get(i).replace("LENGTH", String.valueOf(myLength));
      s = s.replace("ANGLE", String.valueOf(myAngle));
      List<TurtleMethod> parameters = logoParser.parseLine(s);
      myConversions.put(commandList.get(i), parameters);
    }
  }

  /**
   * Parses line of commands
   *
   * @param line of Commands
   * @return List of commands
   */
  @Override
  public List<TurtleMethod> parseLine(String line) {
    ArrayList<TurtleMethod> ret = new ArrayList<>();
    String[] words = line.split("[\s\t]+");
    if (words.length == 0 || words[0].equals("#") || words[0].equals("")) {
      return ret;
    }
    String command = words[0].toUpperCase(Locale.ROOT);
    if (commandsWithArguments.contains(command)) {
      List<Object> parameters = getArguments(words, command);
      if (parameters.size() > 0) {
        ret.add(new TurtleMethod(String.format("%s %s", command, words[1]), parameters));
      }
    } else {
      throw new IncorrectCommand(myErrorResources.getString("IncorrectCommand"), words[0]);
    }
    return ret;
  }

  //Figures out which command was called and calls appropriate method
  private List<Object> getArguments(String[] words, String command) {
    List<Object> ret = new ArrayList<>();
    switch (command) {
      case "START" -> {
        ret = getStartArgument(words);
        if (hasStart) {
          throw new IncorrectCommand(myErrorResources.getString("MultipleStarts"));
        }
        hasStart = true;
        START = words[2];
        return ret;
      }
      case "RULE" -> {
        ret = getRuleArgument(words);
        myRules.put(words[1], words[2]);
        return ret;
      }
      case "SET" -> {
        ret = getSetArgument(words);
        return ret;
      }
    }
    return ret;
  }

  //Gets arguments for the start command
  private List<Object> getStartArgument(String[] words) {
    List<Object> ret;
    if (words.length != 3) {
      throw new IncorrectCommand(myErrorResources.getString("IncorrectCommandStructure"), words[0]);
    }
    if (words[1].equals(":")) {
      ret = checkForArgument(words, 2);
    } else {
      throw new IncorrectCommand(myErrorResources.getString("IncorrectCommandStructure"), words[0]);
    }
    return ret;
  }

  //Gets arguments for the rule command
  private List<Object> getRuleArgument(String[] words) {
    if (words.length != 3) {
      throw new IncorrectCommand(myErrorResources.getString("IncorrectCommandStructure"), words[0]);
    }
    checkName(words[1]);
    return checkForArgument(words, 2);
  }

  //Gets arguments for the set command
  private List<Object> getSetArgument(String[] words) {
    String line = String.join(" ", words);
    String[] split = line.split("\"");
    if (words.length < 3 || split.length != 2) {
      throw new IncorrectCommand(myErrorResources.getString("IncorrectCommandStructure"), words[0]);
    }
    checkName(words[1]);
    String s = split[1].toUpperCase(Locale.ROOT).replace("LENGTH", String.valueOf(myLength));
    s = s.replace("ANGLE", String.valueOf(myAngle));
    List<TurtleMethod> parameters = logoParser.parseLine(s);
    myConversions.put(words[1], parameters);
    return new ArrayList<>();
  }

  //Checks to make sure each character in the string is valid
  protected List<Object> checkForArgument(String[] words, int i) {
    List<Object> ret = new ArrayList<>();
    for (int j = 0; j < words[i].length(); j++) {
      if (!checkCommandArgument(words[i].substring(j, j + 1))) {
        throw new IncorrectCommand(myErrorResources.getString("IncorrectCommandArgument"),
            words[i].substring(j, j + 1));
      }
      ret.add(words[i].substring(j, j + 1));
      myCharacters.add(words[i].substring(j, j + 1));
    }
    return ret;
  }

  //Checks to make sure the name of the command is valid
  private void checkName(String name) {
    if (name.length() != 1) {
      throw new IncorrectCommand(myErrorResources.getString("IncorrectCommandArgument"), name);
    }
    if (!checkForLetter(name)) {
      throw new IncorrectCommand(myErrorResources.getString("IncorrectCommandArgument"), name);
    }
    myCharacters.add(name);
  }

  /**
   * Checks to see if command argument is valid
   *
   * @param arg Command argument
   * @return true/false
   */
  @Override
  public Boolean checkCommandArgument(String arg) {
    if (arg.equals("+") || arg.equals("-")) {
      return true;
    }
    return checkForLetter(arg);
  }

  //Checks to see if string is a letter
  private Boolean checkForLetter(String arg) {
    char c = arg.charAt(0);
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
  }

  /**
   * Checks to see if every character called is either a rule or has a conversion
   */
  public void checkForUnknownCharacters() {
    if (!hasStart) {
      throw new IncorrectCommand(myErrorResources.getString("MissingStart"));
    }
    for (String s : myCharacters) {
      if (!myRules.containsKey(s) && !myConversions.containsKey(s)) {
        throw new IncorrectCommand(myErrorResources.getString("IncorrectCommandArgument"), s);
      }
    }
  }


  /**
   * Resets conversions, character list, and rule list. Also, calls add conversions
   */
  public void resetParser() {
    myConversions = new HashMap<>();
    myCharacters = new HashSet<>();
    myRules = new HashMap<>();
    hasStart = false;
    START = "";
    addConversions();
  }

  /**
   * Compute logo commands for LSystem loop
   *
   * @param loops number of loops
   * @return queue of logo commands
   */
  public LinkedList<TurtleMethod> getLoop(int loops) {
    String commandString = START;
    for (int i = 0; i < loops; i++) {
      String temp = "";
      for (int j = 0; j < commandString.length(); j++) {
        String character = commandString.substring(j, j + 1);
        temp = String.format("%s%s", temp, myRules.getOrDefault(character, character));
      }
      commandString = temp;
    }
    LinkedList<TurtleMethod> ret = new LinkedList<>();
    for (int i = 0; i < commandString.length(); i++) {
      ret.addAll(myConversions.get(commandString.substring(i, i + 1)));
    }
    return ret;
  }
}
