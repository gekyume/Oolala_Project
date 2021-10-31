package oolala.model.codeRunners;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import oolala.model.turtle.TurtleMethod;
import oolala.model.turtle.LogoTurtleModel;
import oolala.util.Bean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogoCodeRunnerTest {

  private static final List<Object> TURTLES = new ArrayList<>(List.of(1, 2, 3, 4));
  private static final List<Object> PARAMETER = new ArrayList<>(List.of(10));
  private static final List<Object> EMPTY = new ArrayList<>();
  private TurtleMethod fd;
  private TurtleMethod bk;
  private TurtleMethod lt;
  private TurtleMethod rt;
  private TurtleMethod pu;
  private TurtleMethod pd;
  private TurtleMethod st;
  private TurtleMethod ht;
  private TurtleMethod stamp;
  private TurtleMethod tell;
  private TurtleMethod home;
  private CodeRunner codeRunner;
  private List<LogoTurtleModel> myTurtles;
  private double myX;
  private double myY;
  private double myDirection;
  private double myStamps;
  private boolean isVisible;

  @BeforeEach
  void init() {
    codeRunner = new LogoCodeRunner();
    fd = new TurtleMethod("FD", PARAMETER);
    bk = new TurtleMethod("BK", PARAMETER);
    lt = new TurtleMethod("LT", PARAMETER);
    rt = new TurtleMethod("RT", PARAMETER);
    pu = new TurtleMethod("PU", EMPTY);
    pd = new TurtleMethod("PD", EMPTY);
    st = new TurtleMethod("ST", EMPTY);
    ht = new TurtleMethod("HT", EMPTY);
    stamp = new TurtleMethod("STAMP", EMPTY);
    tell = new TurtleMethod("TELL", TURTLES);
    home = new TurtleMethod("HOME", EMPTY);
    myTurtles = new ArrayList<>();
    myTurtles.add((LogoTurtleModel) codeRunner.getTurtles().get(0));
    Bean myBean = codeRunner.getBean();
    myX = 0;
    myY = 0;
    myDirection = 0;
    myStamps = 0;
    isVisible = true;
    myBean.addPropertyChangeListener(e -> myTurtles.add((LogoTurtleModel) e.getNewValue()));
    for (Bean bean : myTurtles.get(0).getBeans()) {
      switch (bean.getType()) {
        case "Location" -> bean.addPropertyChangeListener(e -> {
          double[] location = (double[]) e.getNewValue();
          myX = location[0];
          myY = location[1];
        });
        case "Direction" -> bean.addPropertyChangeListener(
            e -> myDirection = (double) e.getNewValue());
        case "Stamp" -> bean.addPropertyChangeListener(e -> myStamps++);
        case "ShowImage" -> bean.addPropertyChangeListener(
            e -> isVisible = (boolean) e.getNewValue());
      }
    }
  }

  @Test
  void GIVEN_noCode_WHEN_runLineCalled_THEN_returnTrue()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    assertTrue(codeRunner.runLine());
  }

  @Test
  void GIVEN_fd_WHEN_runLineCalled_THEN_moveForward_AND_returnFalse()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(List.of(fd)));
    assertFalse(codeRunner.runLine());
    assertEquals(myX, 0);
    assertEquals(myY, -10);
  }

  @Test
  void GIVEN_bk_WHEN_runLineCalled_THEN_moveBackward_AND_returnFalse()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(List.of(bk)));
    assertFalse(codeRunner.runLine());
    assertEquals(myX, 0);
    assertEquals(myY, 10);
  }

  @Test
  void GIVEN_lt_WHEN_runLineCalled_THEN_turnLeft_AND_returnFalse()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(List.of(lt)));
    assertFalse(codeRunner.runLine());
    assertEquals(myDirection, -10);
  }

  @Test
  void GIVEN_rt_WHEN_runLineCalled_THEN_turnRight_AND_returnFalse()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(List.of(rt)));
    assertFalse(codeRunner.runLine());
    assertEquals(myDirection, 10);
  }

  @Test
  void GIVEN_tell_WHEN_runLineCalled_THEN_createNewTurtles_AND_returnFalse()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(List.of(tell)));
    assertFalse(codeRunner.runLine());
    assertEquals(myTurtles, codeRunner.getTurtles());
  }

  @Test
  void GIVEN_pu_WHEN_runLineCalled_THEN_setPenStateFalse_AND_returnFalse()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(List.of(pu)));
    assertFalse(codeRunner.runLine());
    assertFalse(myTurtles.get(0).getPenState());
  }

  @Test
  void GIVEN_pd_WHEN_runLineCalled_THEN_setPenStateTrue_AND_returnFalse()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(List.of(pd)));
    assertFalse(codeRunner.runLine());
    assertTrue(myTurtles.get(0).getPenState());
  }

  @Test
  void GIVEN_st_WHEN_runLineCalled_THEN_setVisibleTrue_AND_returnFalse()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(List.of(st)));
    assertFalse(codeRunner.runLine());
    assertTrue(isVisible);
  }

  @Test
  void GIVEN_ht_WHEN_runLineCalled_THEN_setVisibleFalse_AND_returnFalse()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(List.of(ht)));
    assertFalse(codeRunner.runLine());
    assertFalse(isVisible);
  }

  @Test
  void GIVEN_stamp_WHEN_runLineCalled_THEN_sayStamp_AND_returnFalse()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(List.of(stamp)));
    assertFalse(codeRunner.runLine());
    assertEquals(myStamps, 1);
  }

  @Test
  void GIVEN_newHome_WHEN_setHomeCalled_THEN_setHomeForAllTurtles()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    double[] expected = new double[]{10, 10};
    codeRunner.setHome(expected);
    codeRunner.addCode(new ArrayList<>(List.of(home)));
    codeRunner.runLine();
    assertEquals(myX, expected[0]);
    assertEquals(myY, expected[1]);
  }

  @Test
  void GIVEN_multipleCommands_WHEN_runLineCalled_THEN_iterateThrough()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(
        List.of(fd, bk, pu, pd, st, ht, home, stamp, tell)));
    for (int i = 0; i < 9; i++) {
      assertFalse(codeRunner.runLine());
    }
    assertTrue(codeRunner.runLine());
    assertEquals(codeRunner.getTurtles(), myTurtles);
  }

  @Test
  void GIVEN_newCommands_WHEN_clearCalled_THEN_addNewCommands()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    codeRunner.addCode(new ArrayList<>(List.of(fd, rt)));
    codeRunner.runLine();
    codeRunner.clearCode();
    codeRunner.addCode(new ArrayList<>(List.of(fd)));
    codeRunner.runLine();
    assertEquals(myTurtles.get(0).getDirection(), 0);
  }
}