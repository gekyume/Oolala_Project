package oolala.model.parsers;

import java.util.List;
import java.util.Set;
import oolala.model.exceptions.IncorrectCommand;
import oolala.model.turtle.TurtleMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Darwin Parser
 *
 * @author Kyle White
 */
class DarwinParserTest {

  private Parser myTestInstance;

  @BeforeEach
  void setupForEachTest() {
    myTestInstance = new DarwinParser("English");
  }

  @Test
  void addCommandsWithArgumentsTest() {
    Set<String> mySet = myTestInstance.getCommandsWithArguments();
    String[] commands = myTestInstance.getMyCommandResources().getString("CommandsWithArguments")
        .split(" ");
    for (String s : commands) {
      assertTrue(mySet.contains(s));
    }
    assertFalse(mySet.contains(" "));
  }

  @Test
  void addCommandsWithoutArgumentsTest() {
    Set<String> mySet = myTestInstance.getCommandsWithoutArguments();
    String[] commands = myTestInstance.getMyCommandResources().getString("CommandsWithoutArguments")
        .split(" ");
    for (String s : commands) {
      assertTrue(mySet.contains(s));
    }
    assertFalse(mySet.contains(" "));
  }

  @Test
  void checkCommandArgumentTest() {
    assertTrue(myTestInstance.checkCommandArgument("0"));
    assertTrue(myTestInstance.checkCommandArgument("10"));
    assertFalse(myTestInstance.checkCommandArgument("-1"));
    assertFalse(myTestInstance.checkCommandArgument("cat"));
    assertFalse(myTestInstance.checkCommandArgument(""));
  }

  @Test
  void parseLineTestSuccess() {
    checkSuccess("ifenemy 3 go 5 infect go 1", 4, "IFENEMY 3");
    checkSuccess("ifenemy      3 go      5       infect go 1", 4, "IFENEMY 3");
  }

  private void checkSuccess(String line, int size, String firstCommand) {
    List<TurtleMethod> list = myTestInstance.parseLine(line);
    assertEquals(size, list.size());
    assertEquals(firstCommand, list.get(0).toString());
  }

  @Test
  void parseLineTestFailure() {
    checkException("wrong command", "Incorrect Command: wrong");
    checkException("ifenemy", "Missing Command Argument: ifenemy");
    checkException("go -1", "Incorrect Command Argument: -1");
    checkException("go ifenemy command", "Incorrect Command Argument: ifenemy");
  }

  private void checkException(String line, String expected) {
    Exception exception = assertThrows(IncorrectCommand.class,
        () -> myTestInstance.parseLine(line));
    String actualMessage = exception.getMessage();
    assertEquals(actualMessage, expected);
  }
}