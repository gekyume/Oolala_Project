package oolala.model.codeRunners;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import oolala.model.exceptions.IncorrectCommand;
import oolala.model.turtle.DarwinTurtleModel;
import oolala.model.turtle.TurtleMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DarwinCodeRunnerTest {

  public static final double[] HOME = new double[]{500, 400};
  public static final TurtleMethod MOVE = new TurtleMethod("MOVE", new ArrayList<>(List.of(1)));
  private DarwinCodeRunner runner;
  private DarwinTurtleModel turtle;

  @BeforeEach
  void init() {
    runner = new DarwinCodeRunner("English");
    runner.addTurtle(HOME, 0);
    turtle = (DarwinTurtleModel) runner.getTurtles().get(0);
  }

  @Test
  void GIVEN_enemy_WHEN_infectCalled_THEN_infectIt()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    runner.addTurtle(HOME, 1);
    runner.addCode(new ArrayList<>(List.of(new TurtleMethod("INFECT"))));
    runner.addCode(new ArrayList<>(List.of(new TurtleMethod("MOVE", new ArrayList<>(List.of(1))))));
    runner.runLine();
    runner.runLine();
    DarwinTurtleModel turtle2 = (DarwinTurtleModel) runner.getTurtles().get(1);
    assertEquals(turtle2.getSpecies(), 0);
  }

  @Test
  void GIVEN_noEnemeyWHEN_infectCalled_THEN_doNothing()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    runner.addTurtle(new double[]{100, 100}, 1);
    runner.addCode(new ArrayList<>(List.of(new TurtleMethod("INFECT"))));
    runner.addCode(new ArrayList<>(List.of(new TurtleMethod("MOVE", new ArrayList<>(List.of(1))))));
    runner.runLine();
    runner.runLine();
    DarwinTurtleModel turtle2 = (DarwinTurtleModel) runner.getTurtles().get(1);
    assertEquals(turtle2.getSpecies(), 1);
  }

  @Test
  void GIVEN_line_WHEN_goCalled_THEN_goToThatLine()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    runner.addCode(new ArrayList<>(List.of(MOVE,
        new TurtleMethod("GO", new ArrayList<>(List.of(1))))));
    for (int i = 0; i < 5; i++) {
      runner.runLine();
    }
    assertEquals(turtle.getX(), HOME[0]);
    assertEquals(turtle.getY(), HOME[1] - 3);
  }

  @Test
  void GIVEN_degree_WHEN_leftCalled_THEN_turnDistance()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    int expected = 90;
    runner.addCode(
        new ArrayList<>(List.of(new TurtleMethod("LEFT", new ArrayList<>(List.of(expected))))));
    runner.runLine();
    assertEquals(turtle.getDirection(), -expected);
  }

  @Test
  void GIVEN_degree_WHEN_rightCalled_THEN_turnDistance()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    int expected = 90;
    runner.addCode(
        new ArrayList<>(List.of(new TurtleMethod("RIGHT", new ArrayList<>(List.of(expected))))));
    runner.runLine();
    assertEquals(turtle.getDirection(), expected);
  }

  @Test
  void GIVEN_enemy_WHEN_ifenemyCalled_THEN_jumpToLine()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    int expected = 10;
    runner.addCode(new ArrayList<>(List.of(new TurtleMethod("IFENEMY", new ArrayList<>(List.of(3))),
        MOVE, new TurtleMethod("MOVE", new ArrayList<>(List.of(expected))))));
    runner.addCode(new ArrayList<>(List.of(MOVE, MOVE)));
    runner.addTurtle(new double[]{500, 398}, 1);
    for (int i = 0; i < 3; i++) {
      runner.runLine();
    }
    assertEquals(turtle.getY(), HOME[1] - expected);
  }

  @Test
  void GIVEN_ally_WHEN_ifenemyCalled_THEN_doNotJump()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    int expected = 10;
    runner.addCode(
        new ArrayList<>(List.of(MOVE, new TurtleMethod("IFENEMY", new ArrayList<>(List.of(4))),
            new TurtleMethod("MOVE", new ArrayList<>(List.of(expected))), MOVE)));
    runner.runLine();
    runner.addTurtle(new double[]{500, 398}, 0);
    for (int i = 0; i < 5; i++) {
      runner.runLine();
    }
    assertEquals(turtle.getY(), HOME[1] - expected - 1);
  }

  @Test
  void GIVEN_enemy_WHEN_ifsameCalled_THEN_doNotJump()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    int expected = 10;
    runner.addCode(new ArrayList<>(List.of(new TurtleMethod("IFSAME", new ArrayList<>(List.of(3))),
        new TurtleMethod("MOVE", new ArrayList<>(List.of(expected))), MOVE)));
    runner.addCode(new ArrayList<>(List.of(MOVE, MOVE)));
    runner.addTurtle(new double[]{500, 398}, 1);
    for (int i = 0; i < 3; i++) {
      runner.runLine();
    }
    assertEquals(turtle.getY(), HOME[1] - expected);
  }

  @Test
  void GIVEN_ally_WHEN_ifsameCalled_THEN_doJump()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    int expected = 10;
    runner.addCode(
        new ArrayList<>(List.of(MOVE, new TurtleMethod("IFSAME", new ArrayList<>(List.of(4))),
            MOVE, new TurtleMethod("MOVE", new ArrayList<>(List.of(expected))))));
    runner.runLine();
    runner.addTurtle(new double[]{500, 398}, 0);
    for (int i = 0; i < 5; i++) {
      runner.runLine();
    }
    assertEquals(turtle.getY(), HOME[1] - expected - 1);
  }

  @Test
  void GIVEN_enemy_WHEN_ifemptyCalled_THEN_doNotJump()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    int expected = 10;
    runner.addCode(new ArrayList<>(List.of(new TurtleMethod("IFEMPTY", new ArrayList<>(List.of(3))),
        new TurtleMethod("MOVE", new ArrayList<>(List.of(expected))), MOVE)));
    runner.addCode(new ArrayList<>(List.of(MOVE, MOVE)));
    runner.addTurtle(new double[]{500, 398}, 1);
    for (int i = 0; i < 3; i++) {
      runner.runLine();
    }
    assertEquals(turtle.getY(), HOME[1] - expected);
  }

  @Test
  void GIVEN_nothing_WHEN_ifemptyCalled_THEN_jump()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    int expected = 2;
    runner.addCode(new ArrayList<>(List.of(new TurtleMethod("IFEMPTY", new ArrayList<>(List.of(3))),
        MOVE, new TurtleMethod("MOVE", new ArrayList<>(List.of(expected))))));
    for (int i = 0; i < 2; i++) {
      runner.runLine();
    }
    assertEquals(turtle.getY(), HOME[1] - expected);
  }

  @Test
  void GIVEN_emptyOnlyInFront_WHEN_ifemptyCalled_THEN_jump()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    int expected = 2;
    runner.addCode(new ArrayList<>(List.of(new TurtleMethod("IFEMPTY", new ArrayList<>(List.of(3))),
        MOVE, new TurtleMethod("MOVE", new ArrayList<>(List.of(expected))))));
    runner.addCode(
        new ArrayList<>(List.of(new TurtleMethod("RIGHT", new ArrayList<>(List.of(90))))));
    runner.addTurtle(new double[]{HOME[0] - 1, HOME[1]}, 1);
    runner.addTurtle(new double[]{HOME[0] + 1, HOME[1]}, 1);
    runner.addTurtle(new double[]{HOME[0], HOME[1] + 1}, 1);
    for (int i = 0; i < 5; i++) {
      runner.runLine();
    }
    assertEquals(turtle.getY(), HOME[1] - expected);
  }

  @Test
  void GIVEN_wall_WHEN_ifwallCalled_THEN_jump()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    int expected = 2;
    runner.addCode(new ArrayList<>(List.of(
        new TurtleMethod("MOVE", new ArrayList<>(List.of(345))),
        new TurtleMethod("IFWALL", new ArrayList<>(List.of(4))),
        MOVE, new TurtleMethod("MOVE", new ArrayList<>(List.of(expected))))));
    for (int i = 0; i < 3; i++) {
      runner.runLine();
    }
    assertEquals(turtle.getY(), 55 - expected);
  }

  @Test
  void GIVEN_noWall_WHEN_ifwallCalled_THEN_doNotJump()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    int expected = 2;
    runner.addCode(new ArrayList<>(List.of(
        new TurtleMethod("IFWALL", new ArrayList<>(List.of(4))),
        new TurtleMethod("MOVE", new ArrayList<>(List.of(expected))), MOVE)));
    for (int i = 0; i < 2; i++) {
      runner.runLine();
    }
    assertEquals(turtle.getY(), HOME[1] - expected);
  }

  @Test
  void GIVEN_offWall_WHEN_moveCalled_THEN_doNotMove()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    runner.addCode(
        new ArrayList<>(List.of(new TurtleMethod("MOVE", new ArrayList<>(List.of(350))))));
    runner.runLine();
    assertEquals(turtle.getX(), HOME[0]);
    assertEquals(turtle.getY(), HOME[1]);
  }

  @Test
  void GIVEN_landOnOtherTurtle_WHEN_moveCalled_THEN_doNotMove()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    runner.addCode(new ArrayList<>(List.of(MOVE)));
    runner.addCode(
        new ArrayList<>(List.of(new TurtleMethod("RIGHT", new ArrayList<>(List.of(90))))));
    runner.addTurtle(new double[]{HOME[0], HOME[1] - 1}, 1);
    for (int i = 0; i < 2; i++) {
      runner.runLine();
    }
    assertEquals(turtle.getX(), HOME[0]);
    assertEquals(turtle.getY(), HOME[1]);
  }

  @Test
  void GIVEN_noTurtles_WHEN_runLineCalled_THEN_returnTrue()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    DarwinCodeRunner emptyRunner = new DarwinCodeRunner("English");
    emptyRunner.addCode(new ArrayList<>(List.of(MOVE)));
    assertTrue(emptyRunner.runLine());
  }

  @Test
  void GIVEN_noCodeForGivenSpecies_WHEN_runLineCalled_THEN_throwNoSpeciesCodeError() {
    checkException("Species 0 missing code");
  }

  @Test
  void GIVEN_nonLoopingCode_WHEN_runLineCalled_THEN_throwNonLoopingCodeError()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    runner.addCode(new ArrayList<>(List.of(MOVE)));
    runner.runLine();
    checkException("Species 0 does not indefinitely loop");
  }

  @Test
  void GIVEN_newCode_WHEN_clearCalled_THEN_runNewCode()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    int expected = 90;
    runner.addCode(new ArrayList<>(List.of(MOVE)));
    runner.runLine();
    runner.clearCode();
    runner.addCode(new ArrayList<>(List.of(new TurtleMethod("RIGHT", List.of(expected)))));
    runner.runLine();
    assertEquals(turtle.getDirection(), expected);
  }

  private void checkException(String expected) {
    Exception exception = assertThrows(IncorrectCommand.class,
        () -> runner.runLine());
    String actualMessage = exception.getMessage();
    assertEquals(actualMessage, expected);
  }
}