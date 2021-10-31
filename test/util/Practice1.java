package util;

import java.util.ArrayList;
import java.util.List;
import oolala.util.Bean;

/**
 * This class is used to test how a turtle VIEW might use a property change listener to communicate
 * with a view
 *
 * @author Nolan Burroughs
 */
public class Practice1 {

  private List<Integer> coordinates;
  private Integer rotation;
  private Bean myLocationBean;
  private Bean myRotationBean;
  private List<Bean> myBeans;

  /**
   * Constructs an object with a bean, coordinates, and rotation
   */
  public Practice1() {
    myLocationBean = new Bean("Location");
    myRotationBean = new Bean("Rotation");
    myBeans = new ArrayList<>(List.of(myLocationBean, myRotationBean));
    coordinates = new ArrayList<>(List.of(1, 2));
    rotation = 0;
  }

  /**
   * Moves the coordinates in the direction it is rotated and sends a property changed event
   *
   * @param distance Pixels to move
   */
  public void move(Integer distance) {
    List<Integer> newCoordinates = new ArrayList<>();
    newCoordinates.add((int) (distance * Math.cos(rotation) + coordinates.get(0)));
    newCoordinates.add((int) (distance * Math.sin(rotation) + coordinates.get(1)));
    coordinates = newCoordinates;
    myLocationBean.setProperty(newCoordinates);
  }

  /**
   * Rotates by the amount of degrees given and sends a property changed event
   *
   * @param degrees Degrees to rotate
   */
  public void rotate(Integer degrees) {
    rotation = rotation + degrees;
    myRotationBean.setProperty(rotation);
  }

  /**
   * @return Personal bean
   */
  public List<Bean> getBeans() {
    return myBeans;
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
