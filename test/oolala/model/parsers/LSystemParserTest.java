package oolala.model.parsers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oolala.model.exceptions.IncorrectCommand;
import oolala.model.turtle.TurtleMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the LSystem Parser
 *
 * @author Kyle White
 */
class LSystemParserTest {

  private LSystemParser myTestInstance;

  @BeforeEach
  void setupForEachTest() {
    myTestInstance = new LSystemParser("English", 30, 10);
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
  void addConversionsTest() {
    Map<String, List<TurtleMethod>> map = myTestInstance.getConversions();
    String[] commands = myTestInstance.getMyCommandResources().getString("CommandsWithoutArguments")
        .split(" ");
    for (String s : commands) {
      assertTrue(map.containsKey(s));
    }
    assertFalse(map.containsKey(" "));
  }

  @Test
  void checkCommandArgumentTest() {
    assertTrue(myTestInstance.checkCommandArgument("+"));
    assertTrue(myTestInstance.checkCommandArgument("-"));
    assertTrue(myTestInstance.checkCommandArgument("a"));
    assertTrue(myTestInstance.checkCommandArgument("B"));
    assertFalse(myTestInstance.checkCommandArgument("1"));
    assertFalse(myTestInstance.checkCommandArgument("/"));
    assertFalse(myTestInstance.checkCommandArgument(" "));
  }

  @Test
  void parseLineTestSuccess() {
    List<TurtleMethod> list = myTestInstance.parseLine("START : F");
    assertEquals("START : F", list.get(0).toString());
    myTestInstance.resetParser();
    list = myTestInstance.parseLine("start : F");
    assertEquals("START : F", list.get(0).toString());
    list = myTestInstance.parseLine("rule T F-G-F");
    assertEquals("RULE T F - G - F", list.get(0).toString());
    list = myTestInstance.parseLine("Rule     F        FFF");
    assertEquals("RULE F F F F", list.get(0).toString());
    list = myTestInstance.parseLine("set z        \"PD FD LENGTH\"");
    assertEquals(0, list.size());
    Map<String, List<TurtleMethod>> map = myTestInstance.getConversions();
    assertTrue(map.containsKey("z"));
    list = myTestInstance.parseLine("set z \"pu bk length\"");
    assertEquals(0, list.size());
    map = myTestInstance.getConversions();
    assertTrue(map.containsKey("z"));
    list = myTestInstance.parseLine("set z \"pu rt ANGLE\"");
    assertEquals(0, list.size());
    map = myTestInstance.getConversions();
    assertTrue(map.containsKey("z"));
  }

  @Test
  void parseLineTestFailure() {
    checkException("Start F", "Incorrect Command Structure: Start");
    checkException("START ; F", "Incorrect Command Structure: START");
    checkException("STAR : F", "Incorrect Command: STAR");
    checkException("START : F1F", "Incorrect Command Argument: 1");
    checkException("RULE wrong F-G-F", "Incorrect Command Argument: wrong");
    checkException("RULE 1 F-G-F", "Incorrect Command Argument: 1");
    checkException("RULE F F-.-F", "Incorrect Command Argument: .");
    checkException("SET wrong \"PD FD LENGTH\"", "Incorrect Command Argument: wrong");
    checkException("SET 1 \"PD FD LENGTH\"", "Incorrect Command Argument: 1");
    checkException("SET z f-f-d", "Incorrect Command Structure: SET");
    checkException("SET F \"wrong FD LENGTH\"", "Incorrect Command: WRONG");
    checkException("SET F \"PD FD\"", "Missing Command Argument: FD");
  }

  //Calls parseLine on line and checks to see if the error message equals expected
  private void checkException(String line, String expected) {
    Exception exception = assertThrows(IncorrectCommand.class,
        () -> myTestInstance.parseLine(line));
    String actualMessage = exception.getMessage();
    assertEquals(actualMessage, expected);
  }

  @Test
  void checkForUnknownCharactersTest() {
    myTestInstance.parseLine("Start : F");
    myTestInstance.checkForUnknownCharacters();
    myTestInstance.resetParser();
    myTestInstance.parseLine("Rule T F");
    myTestInstance.parseLine("Start : T");
    myTestInstance.checkForUnknownCharacters();
    myTestInstance.parseLine("Set z \"PD FD LENGTH\"");
    myTestInstance.checkForUnknownCharacters();
    myTestInstance.parseLine("RUle H zzz");
    myTestInstance.checkForUnknownCharacters();
    myTestInstance.parseLine("RUle H y");
    Exception exception = assertThrows(IncorrectCommand.class,
        () -> myTestInstance.checkForUnknownCharacters());
    String expected = "Incorrect Command Argument: y";
    String actualMessage = exception.getMessage();
    assertEquals(actualMessage, expected);
  }

  @Test
  void getLoopTest() {
    myTestInstance.parseLine("Start : F");
    myTestInstance.parseLine("Rule F F+F+F");
    LinkedList<TurtleMethod> queue = myTestInstance.getLoop(0);
    assertEquals(2, queue.size());
    queue = myTestInstance.getLoop(1);
    assertEquals(8, queue.size());
    queue = myTestInstance.getLoop(2);
    assertEquals(26, queue.size());
    myTestInstance.resetParser();
    myTestInstance.parseLine("start  : T");
    myTestInstance.parseLine("rule T U-T-U");
    myTestInstance.parseLine("rule U T+U+T");
    myTestInstance.parseLine("set T \"pd fd LENGTH\"");
    myTestInstance.parseLine("set U \"pd fd LENGTH\"");
    myTestInstance.checkForUnknownCharacters();
    queue = myTestInstance.getLoop(0);
    assertEquals(2, queue.size());
    queue = myTestInstance.getLoop(1);
    assertEquals(8, queue.size());
    queue = myTestInstance.getLoop(2);
    assertEquals(26, queue.size());
  }
}