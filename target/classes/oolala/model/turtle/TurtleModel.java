package oolala.model.turtle;

import java.util.ArrayList;
import java.util.List;
import oolala.util.Bean;

/**
 * This class is the abstract implementation of a turtle model with common commands (move, rotate,
 * pen state) that uses property change listener beans to communicate with the view.
 *
 * @author Nolan Burroughs
 * @author Casey Goldstein
 */
public abstract class TurtleModel {

  protected double[] myHome;
  protected List<Bean> myBeans;
  protected Bean myLocationBean;
  protected Bean myDirectionBean;
  protected double myX;
  protected double myY;
  protected double myDirection;
  protected boolean myPenState;

  /**
   * Constructs a base turtle model object with a home state and beans
   *
   * @param homeTuple Starting home location
   * @param penState  Starting pen state
   */
  public TurtleModel(double[] homeTuple, boolean penState) {
    myHome = homeTuple;
    myX = homeTuple[0];
    myY = homeTuple[1];
    myDirection = 0;
    myPenState = penState;
    myBeans = new ArrayList<>();
    myLocationBean = addBean("Location", new double[]{myX, myY});
    myDirectionBean = addBean("Direction", myDirection);
  }

  /**
   * @return Beans for this object
   */
  public List<Bean> getBeans() {
    return myBeans;
  }

  /**
   * @return direction of turtle
   */
  public double getDirection() {
    return myDirection;
  }

  /**
   * Moves the coordinates in the give distance with the current direction in javafx coordinate
   * system
   *
   * @param distance Pixels to move
   */
  public void move(double distance) {
    double angle = -Math.toRadians(myDirection);
    double newX = (myX - distance * Math.sin(angle));
    double newY = (myY - distance * Math.cos(angle));
    myX = newX;
    myY = newY;
    myLocationBean.setProperty(new double[]{myX, myY});
  }

  /**
   * Rotates the direction the given amount in javafx coordinates
   *
   * @param degrees Degrees to rotate
   */
  public void rotate(double degrees) {
    myDirection = myDirection - degrees;
    myDirectionBean.setProperty(myDirection);
  }

  /**
   * @param newState Whether pen is down
   */
  public void setPenState(boolean newState) {
    myPenState = newState;
  }

  /**
   * @return Current pen state
   */
  public boolean getPenState() {
    return myPenState;
  }

  /**
   * @param newHome Set home to given home
   */
  public void setHome(double[] newHome) {
    myHome = newHome;
  }

  /**
   * @return Current x-coordinate (javafx coordinate system)
   */
  public double getX() {
    return myX;
  }

  /**
   * @return Current y-coordinate (javafx coordinate system)
   */
  public double getY() {
    return myY;
  }

  /**
   * Constructs a new bean with the given type, sets its original value, and adds it to myBeans
   *
   * @param type          Type of Bean, used for telling beans apart in view
   * @param originalValue Original value of bean
   * @return The constructed bean
   */
  protected Bean addBean(String type, Object originalValue) {
    Bean bean = new Bean(type);
    bean.setProperty(originalValue);
    myBeans.add(bean);
    return bean;
  }
}
