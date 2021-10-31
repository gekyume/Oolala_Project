package oolala.model.turtle;

import static org.junit.jupiter.api.Assertions.*;

import oolala.util.Bean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogoTurtleModelTest {

  private LogoTurtleModel myLogoTurtle;
  private double myX;
  private double myY;
  private double myDirection;
  private int myStamps;
  private boolean isVisible;

  @BeforeEach
  void init() {
    double[] home = new double[2];
    home[0] = 0;
    home[1] = 0;
    myLogoTurtle = new LogoTurtleModel(home);
    myX = 0;
    myY = 0;
    myDirection = 0;
    myStamps = 0;
    isVisible = true;
    for (Bean bean : myLogoTurtle.getBeans()) {
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
  void forward() {
    assertEquals(myX, 0);
    assertEquals(myY, 0);
    myLogoTurtle.move(10);
    assertEquals(myX, 0);
    assertEquals(myY, -10);
  }

  @Test
  void back() {
    assertEquals(myX, 0);
    assertEquals(myY, 0);
    myLogoTurtle.move(-10);
    assertEquals(myX, 0);
    assertEquals(myY, 10);
  }

  @Test
  void rotateLeft() {
    assertEquals(myDirection, 0);
    myLogoTurtle.rotate(90);
    assertEquals(myDirection, -90);
  }

  @Test
  void rotateRight() {
    assertEquals(myDirection, 0);
    myLogoTurtle.rotate(-90);
    assertEquals(myDirection, 90);
  }

  @Test
  void stamp() {
    assertEquals(myStamps, 0);
    myLogoTurtle.stamp();
    assertEquals(myStamps, 1);
    myLogoTurtle.stamp();
    assertEquals(myStamps, 2);
  }

  @Test
  void hideTurtle() {
    assertTrue(isVisible);
    myLogoTurtle.setVisible(false);
    assertFalse(isVisible);
  }

  @Test
  void showTurtle() {
    assertTrue(isVisible);
    myLogoTurtle.setVisible(false);
    myLogoTurtle.setVisible(true);
    assertTrue(isVisible);
  }

  @Test
  void home() {
    myLogoTurtle.move(50);
    myLogoTurtle.rotate(90);
    myLogoTurtle.move(50);
    assertEquals(myX, -50);
    assertEquals(myY, -50);
    assertEquals(myDirection, -90);
    myLogoTurtle.home();
    assertEquals(myX, 0);
    assertEquals(myY, 0);
    assertEquals(myDirection, 0);
  }

  @Test
  void setPen() {
    assertTrue(myLogoTurtle.getPenState());
    myLogoTurtle.setPenState(false);
    assertFalse(myLogoTurtle.getPenState());
    myLogoTurtle.setPenState(true);
    assertTrue(myLogoTurtle.getPenState());
  }

  @Test
  void manyCommands() {
    myLogoTurtle.move(50);
    myLogoTurtle.rotate(-90);
    myLogoTurtle.move(50);
    myLogoTurtle.move(50);
    myLogoTurtle.rotate(-90);
    myLogoTurtle.setVisible(false);
    assertEquals(myDirection, 180);
    assertEquals(myX, 100);
    assertEquals(myY, -50);
    assertFalse(isVisible);
  }
}