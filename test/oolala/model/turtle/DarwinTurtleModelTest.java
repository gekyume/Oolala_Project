package oolala.model.turtle;

import static org.junit.jupiter.api.Assertions.*;

import oolala.util.Bean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DarwinTurtleModelTest {

  private static final double[] HOME = new double[]{500, 400};
  private DarwinTurtleModel myTurtle;
  private int speciesFollower;

  @BeforeEach
  void init() {
    myTurtle = new DarwinTurtleModel(HOME, 0);
    speciesFollower = myTurtle.getSpecies();
    for (Bean bean : myTurtle.getBeans()) {
      if (bean.getType().equals("Species")) {
        bean.addPropertyChangeListener(e -> {
          speciesFollower = (int) e.getNewValue();
        });
      }
    }
  }

  @Test
  void GIVEN_newSpecies_WHEN_setSpeciesCalled_THEN_changeSpecies() {
    int expected = 1;
    myTurtle.setSpecies(expected);
    assertEquals(myTurtle.getSpecies(), expected);
  }

  @Test
  void GIVEN_newSpecies_WHEN_setSpeciesCalled_THEN_messageBean() {
    int expected = 1;
    myTurtle.setSpecies(expected);
    assertEquals(speciesFollower, expected);
  }
}