package oolala.model.turtle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class TurtleMethodTest {

  private final List<Object> MULTI_LIST = new ArrayList<>(List.of(1, 2));
  private final List<Object> SINGLE_LIST = new ArrayList<>(List.of(3));

  @Test
  void GIVEN_noParameters_WHEN_getMethodNameCalled_THEN_returnMethodName() {
    TurtleMethod testTurtle = new TurtleMethod("pd");
    assertEquals(testTurtle.getMethodName(), "pd");
  }

  @Test
  void GIVEN_parameters_WHEN_getMethodNameCalled_THEN_returnMethodName() {
    TurtleMethod testTurtle = new TurtleMethod("fd", SINGLE_LIST);
    assertEquals(testTurtle.getMethodName(), "fd");
  }

  @Test
  void GIVEN_noParameters_WHEN_getParametersCalled_THEN_returnEmptyList() {
    TurtleMethod testTurtle = new TurtleMethod("pd");
    assertEquals(testTurtle.getParameters(), new ArrayList<>());
  }

  @Test
  void GIVEN_parameters_WHEN_getParametersCalled_THEN_returnParameters() {
    TurtleMethod testTurtle = new TurtleMethod("tell", MULTI_LIST);
    assertEquals(testTurtle.getParameters(), MULTI_LIST);
  }

  @Test
  void GIVEN_noParameters_WHEN_toStringCalled_THEN_returnMethodName() {
    TurtleMethod testTurtle = new TurtleMethod("pu");
    assertEquals(testTurtle.toString(), "pu");
  }

  @Test
  void GIVEN_oneParameter_WHEN_toStringCalled_THEN_returnMethodNameSpaceNumber() {
    TurtleMethod testTurtle = new TurtleMethod("fd", SINGLE_LIST);
    assertEquals(testTurtle.toString(), "fd 3");
  }

  @Test
  void GIVEN_multipleParameters_WHEN_toStringCalled_THEN_returnMethodName_AND_spacedParameters() {
    TurtleMethod testTurtle = new TurtleMethod("tell", MULTI_LIST);
    assertEquals(testTurtle.toString(), "tell 1 2");
  }
}