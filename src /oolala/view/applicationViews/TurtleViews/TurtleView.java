package oolala.view.applicationViews.TurtleViews;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import oolala.model.turtle.TurtleModel;
import oolala.util.Bean;

/**
 * This class represents a turtle view object using new beans to set locations, rotation, and
 * stamp.
 *
 * @author Nolan Burroughs
 * @author Casey Goldstein
 */
public abstract class TurtleView {

  public static final int IMAGE_SIZE = 50;
  protected ImageView myTurtle;
  protected TurtleModel myTurtleModel;
  protected Image myCurrentImage;
  protected Group myDisplayNode;
  protected double myPenSize;
  protected Paint myPenColor;
  protected Group myLines;


  /**
   * Constructs a turtle view object with an image and sets up the beans to listen for changes in
   * the model
   *
   * @param turtleModel Matched turtle model
   */
  public TurtleView(TurtleModel turtleModel) {
    myTurtleModel = turtleModel;
    makeDefaultTurtle();
    setDefaultPen();
    myLines = new Group();
    myDisplayNode = new Group(myTurtle, myLines);
    for (Bean bean : myTurtleModel.getBeans()) {
      switch (bean.getType()) {
        case "Location" -> bean.addPropertyChangeListener(
            e -> move((double[]) e.getOldValue(), (double[]) e.getNewValue()));
        case "Direction" -> bean.addPropertyChangeListener(e -> myTurtle.setRotate(
            (Double) e.getNewValue()));
        case "ShowImage" -> bean.addPropertyChangeListener(e -> myTurtle.setVisible(
            (Boolean) e.getNewValue()));
      }
    }
  }

  /**
   * @return The node containing every made image for this turtle
   */
  public Node getDisplayNode() {
    return myDisplayNode;
  }


  /**
   * Sets the turtle image to the one stored at the given path
   *
   * @param pathname Path to the new turtle image
   */
  public void setImage(String pathname) {
    myTurtle.setImage(new Image(pathname));
  }


  /**
   * Moves the turtle from the old location to the new and draws a line if pen is down
   *
   * @param oldLocation Old x, y coordinates
   * @param newLocation New x, y coordinates
   */
  protected void move(double[] oldLocation, double[] newLocation) {
    myTurtle.setX(newLocation[0] - myTurtle.getFitWidth() / 2);
    myTurtle.setY(newLocation[1] - myTurtle.getFitHeight() / 2);

    if (myTurtleModel.getPenState()) {
      makeLine(oldLocation, newLocation);
    }
  }



  /**
   * Sets the pen stroke width to the given thickness
   *
   * @param thickness New thickness
   */
  public void setPenThickness(double thickness) {
    myPenSize = thickness;
  }

  /**
   * Sets the pen stroke color to the given colo
   *
   * @param color New color
   */
  public void setPenColor(Paint color) {
    myPenColor = color;
  }

  /**
   * Creates default ImageView Turtle object and sets it to middle of the screen
   */
  public void makeDefaultTurtle() {
    myCurrentImage = new Image("oolala/view/images/black.png");
    myTurtle = new ImageView(myCurrentImage);
    myTurtle.setId("TurtleImage");
    myTurtle.setFitHeight(IMAGE_SIZE);
    myTurtle.setFitWidth(IMAGE_SIZE);
    myTurtle.setX(myTurtleModel.getX() - myTurtle.getFitWidth() / 2);
    myTurtle.setY(myTurtleModel.getY() - myTurtle.getFitHeight() / 2);
  }

  /**
   * Set the default pen with size 1 and black color
   */
  public void setDefaultPen() {
    myPenSize = 1;
    myPenColor = Color.BLACK;
  }

  /**
   * Draws Line object using Pen's attributes, and locations passed into TurtleView constructor
   *
   * @param oldLocation Initial location of ImageView myTurtle
   * @param newLocation New location of ImageView myTurtle
   */
  public void makeLine(double[] oldLocation, double[] newLocation) {
    Line line = new Line(oldLocation[0], oldLocation[1], newLocation[0], newLocation[1]);
    line.setStrokeWidth(myPenSize);
    line.setStroke(myPenColor);
    line.setId("Line");
    myLines.getChildren().add(line);
  }

}
