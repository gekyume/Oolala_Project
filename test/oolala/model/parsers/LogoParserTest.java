package oolala.model.parsers;

import java.util.List;
import java.util.Set;
import oolala.model.exceptions.IncorrectCommand;
import oolala.model.turtle.TurtleMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Logo Parser
 *
 * @author Kyle White
 */
class LogoParserTest {

  private Parser myTestInstance;

  @BeforeEach
  void setupForEachTest() {
    myTestInstance = new LogoParser("English");
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
    checkSuccess("FD 50", 1, "FD 50");
    checkSuccess("TELL 1 2 3 4 5 6 7 8 9 10    11        12        ", 1, "TELL 1 2 3 4 5 6 7 8 9 10 11 12");
    checkSuccess("fd 50", 1, "FD 50");
    checkSuccess("Fd 50", 1, "FD 50");
    checkSuccess("FD    50", 1, "FD 50");
    checkSuccess("FD 50 BK 100 LT 1 RT 0 PD PU ST HT HOME STAMP TELL 1 2 3 4", 11, "FD 50");
    List <TurtleMethod> list = myTestInstance.parseLine("# This is a comment");
    assertEquals(0, list.size());
    list = myTestInstance.parseLine("         ");
    assertEquals(0, list.size());
  }

  private void checkSuccess(String line, int size, String firstCommand) {
    List<TurtleMethod> list = myTestInstance.parseLine(line);
    assertEquals(size, list.size());
    assertEquals(firstCommand, list.get(0).toString());
  }

  @Test
  void parseLineTestFailure() {
    checkException("wrong command", "Incorrect Command: wrong");
    checkException("TELL", "Missing Command Argument: TELL");
    checkException("FD -1", "Incorrect Command Argument: -1");
    checkException("Tell 1 2 3 cat", "Incorrect Command: cat");
    checkException("FD BK", "Incorrect Command Argument: BK");
  }

  private void checkException(String line, String expected) {
    Exception exception = assertThrows(IncorrectCommand.class,
        () -> myTestInstance.parseLine(line));
    String actualMessage = exception.getMessage();
    assertEquals(actualMessage, expected);
  }
}