package util;

import java.util.ArrayList;
import java.util.List;
import oolala.util.Bean;

/**
 * This class is used to test how a turtle VIEW might use a property change listener to follow the
 * model
 *
 * @author Nolan Burroughs
 */
public class Practice2 {

  private List<Integer> coordinates;
  private Integer rotation;

  /**
   * Makes an object with a coordinate and rotation and sets a property listener to follow the
   * model
   *
   * @param practice1 Practice Model object
   */
  public Practice2(Practice1 practice1) {
    List<Bean> beans = practice1.getBeans();
    coordinates = new ArrayList<>(List.of(0, 3));
    rotation = 7;
    for (Bean bean : beans) {
      switch (bean.getType()) {
        case "Location" -> bean.addPropertyChangeListener(
            e -> coordinates = (List<Integer>) e.getNewValue());
        case "Rotation" -> bean.addPropertyChangeListener(
            e -> rotation = (Integer) e.getNewValue());
      }
    }
  }

  /**
   * @return Current coordinates
   */
  public List<Integer> getCoordinates() {
    return coordinates;
  }

  /**
   * @return Current rotation
   */
  public Integer getRotation() {
    return rotation;
  }
}
