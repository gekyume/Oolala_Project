package oolala.view.applicationViews.TurtleViews;


import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import oolala.model.turtle.LogoTurtleModel;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import oolala.view.applicationViews.TurtleViews.TurtleView;

class TurtleViewTest extends DukeApplicationTest {

  private LogoTurtleModel myTurtleModel;
  private LogoTurtleView myTurtleView;

  @Override
  public void start(Stage stage) {
    myTurtleModel = new LogoTurtleModel(new double[]{100, 100});
    myTurtleView = new LogoTurtleView(myTurtleModel);
    stage.setScene(new Scene((Parent) myTurtleView.getDisplayNode(), 500, 500));
  }

  @Test
  void GIVEN_penDown_WHEN_moveCalled_THEN_move_AND_drawLine() {
    myTurtleModel.move(10);
    assertEquals(myTurtleView.myTurtle.getX(), 75);
    assertEquals(myTurtleView.myTurtle.getY(), 65);
    assertEquals(myTurtleView.myLines.getChildren().size(), 1);
  }

  @Test
  void GIVEN_penUp_WHEN_moveCalled_THEN_move() {
    myTurtleModel.setPenState(false);
    myTurtleModel.move(-10);
    assertEquals(myTurtleView.myTurtle.getX(), 75);
    assertEquals(myTurtleView.myTurtle.getY(), 85);
    assertEquals(myTurtleView.myLines.getChildren().size(), 0);
  }

  @Test
  void GIVEN_image_WHEN_stampCalled_THEN_stampImage() {
    assertEquals(myTurtleView.myStamps.getChildren().size(), 0);
    myTurtleModel.stamp();
    assertEquals(myTurtleView.myStamps.getChildren().size(), 1);
  }

  @Test
  void GIVEN_leftTurn_WHEN_rotateCalled_THEN_rotateImageLeft() {
    myTurtleModel.rotate(45);
    assertEquals(myTurtleView.myTurtle.getRotate(), -45);
  }

  @Test
  void GIVEN_rightTurn_WHEN_rotateCalled_THEN_rotateImageRight() {
    myTurtleModel.rotate(-45);
    assertEquals(myTurtleView.myTurtle.getRotate(), 45);
  }

  @Test
  void GIVEN_pink_WHEN_setPenColorCalled_THEN_drawPinkLines() {
    Color original = Color.BLACK;
    Color expected = Color.PINK;
    myTurtleModel.move(50);
    Line line = (Line) myTurtleView.myLines.getChildren().get(0);
    assertEquals(line.getStroke(), original);
    myTurtleView.setPenColor(expected);
    myTurtleModel.move(50);
    Line line2 = (Line) myTurtleView.myLines.getChildren().get(1);
    assertEquals(line2.getStroke(), expected);
  }

  @Test
  void GIVEN_five_WHEN_setPenThicknessCalled_THEN_drawWithWidthFive() {
    double original = 1;
    double expected = 5;
    myTurtleModel.move(50);
    Line line = (Line) myTurtleView.myLines.getChildren().get(0);
    assertEquals(line.getStrokeWidth(), original);
    myTurtleView.setPenThickness(expected);
    myTurtleModel.move(50);
    Line line2 = (Line) myTurtleView.myLines.getChildren().get(1);
    assertEquals(line2.getStrokeWidth(), expected);
  }

  @Test
  void GIVEN_newImagePath_WHEN_setImageCalled_THEN_changeImageAtSameLocation() {
    myTurtleModel.move(10);
    myTurtleView.setImage("oolala/view/images/blue.png");
    Image image = myTurtleView.myTurtle.getImage();
    assertEquals(myTurtleView.myTurtle.getX(), 75);
    assertEquals(myTurtleView.myTurtle.getY(), 65);
  }
}