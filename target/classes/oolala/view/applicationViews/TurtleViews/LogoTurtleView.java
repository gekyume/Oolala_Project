package oolala.view.applicationViews.TurtleViews;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import oolala.model.turtle.TurtleModel;
import oolala.util.Bean;

/**
 * This class represents the turtle view for a logo program.  It contains extra functionality for
 * stamping image.
 *
 * @author Nolan Burroughs
 * @author Casey Goldstein
 */
public class LogoTurtleView extends TurtleView {

  protected Group myStamps;

  /**
   * Constructs a turtle view with an extra set of stamps and a bean listening for stamp calls.
   *
   * @param myTurtleModel Inputted turtle model
   */
  public LogoTurtleView(TurtleModel myTurtleModel) {
    super(myTurtleModel);
    myStamps = new Group();
    myDisplayNode.getChildren().add(myStamps);
    for (Bean bean : myTurtleModel.getBeans()) {
      if (bean.getType().equals("Stamp")) {
        bean.addPropertyChangeListener(e -> stamp());
      }
    }
  }

  /**
   * Stamps a copy of the turtle at the current position
   */
  private void stamp() {
    ImageView stamp = new ImageView(myCurrentImage);
    stamp.setX(myTurtle.getX());
    stamp.setY(myTurtle.getY());
    stamp.setFitWidth(myTurtle.getFitWidth());
    stamp.setFitHeight(myTurtle.getFitHeight());
    stamp.setRotate(myTurtle.getRotate());
    myStamps.getChildren().add(stamp);
  }

}
