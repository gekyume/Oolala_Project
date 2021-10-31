package oolala.view.applicationViews.TurtleScreens;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oolala.model.codeRunners.CodeRunner;
import oolala.view.applicationViews.TurtleViews.LogoTurtleView;
import oolala.view.applicationViews.TurtleViews.TurtleView;

/**
 * This class represents the screen for displaying all the turtles
 *
 * @author Nolan Burroughs
 * @author Casey Goldstein
 */
public abstract class TurtleScreen {

  public static final double SCREEN_WIDTH = 650;
  public static final double SCREEN_HEIGHT = 600;
  protected Group myScreenNode;
  protected Rectangle myScreen;
  protected double[] currentHome;
  protected List<TurtleView> myTurtles;

  /**
   * Makes the group containing the turtle screen and a new screen
   */
  public TurtleScreen(CodeRunner codeRunner) {
    myTurtles = new ArrayList<>();
    myScreenNode = new Group();
    myScreenNode.setId("ScreenNode");
    myScreen = new Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT);
    myScreen.setId("Screen");
    myScreenNode.getChildren().add(myScreen);
    currentHome = new double[]{myScreen.getWidth() / 2, myScreen.getHeight() / 2};
    codeRunner.setHome(currentHome);
  }

  /**
   * @return The node for the display
   */
  public Node getScreenNode() {
    return myScreenNode;
  }

  /**
   * Sets the screen to the chosen color
   *
   * @param color The new color
   */
  public void setScreenColor(Color color) {
    myScreen.setFill(color);
  }


  /**
   * Sets the image of all the turtles
   *
   * @param path New image path
   */
  public void setTurtleImage(String path) {
    for (TurtleView turtle : myTurtles) {
      turtle.setImage(path);
    }
  }

  /**
   * Sets the pen thickness of all the turtles
   *
   * @param thickness New thickness
   */
  public void setPenThickness(double thickness) {
    for (TurtleView turtle : myTurtles) {
      LogoTurtleView convertedTurtle = (LogoTurtleView) turtle;
      convertedTurtle.setPenThickness(thickness);
    }
  }

  /**
   * Sets the pen color of all the turtles
   *
   * @param color New color
   */
  public void setPenColor(Color color) {
    for (TurtleView turtle : myTurtles) {
      LogoTurtleView convertedTurtle = (LogoTurtleView) turtle;
      convertedTurtle.setPenColor(color);
    }
  }

}