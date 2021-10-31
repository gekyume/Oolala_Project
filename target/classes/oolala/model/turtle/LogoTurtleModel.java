package oolala.model.turtle;

import oolala.util.Bean;

/**
 * Turtle model used for Logo.  Has some methods unique to Logo such as hiding the turtle, stamping
 * the image, and moving to home.
 *
 * @author Nolan Burroughs
 * @author Casey Goldstein
 */
public class LogoTurtleModel extends TurtleModel {

  protected Bean myImageShowBean;
  protected Bean myStampBean;
  protected boolean isVisible;

  protected boolean stamp;

  /**
   * Constructs a logo turtle model at a given home that starts visible, with pen down, and makes
   * unique beans
   *
   * @param homeTuple Home location
   */
  public LogoTurtleModel(double[] homeTuple) {
    super(homeTuple, true);
    isVisible = true;
    stamp = true;
    myImageShowBean = addBean("ShowImage", isVisible);
    myStampBean = addBean("Stamp", stamp);
  }

  /**
   * Updates visibility and fires method change event
   *
   * @param visible Whether it should be visible
   */
  public void setVisible(boolean visible) {
    isVisible = visible;
    myImageShowBean.setProperty(isVisible);
  }

  /**
   * Fires off a step event by changing the step value because value has to change for event to be
   * fired
   */
  public void stamp() {
    stamp = !stamp;
    myStampBean.setProperty(stamp);
  }

  /**
   * Moves turtle to home and puts direction back to 0
   */
  public void home() {
    myX = myHome[0];
    myY = myHome[1];
    myLocationBean.setProperty(myHome);
    myDirection = 0;
    myDirectionBean.setProperty(myDirection);
  }
}
