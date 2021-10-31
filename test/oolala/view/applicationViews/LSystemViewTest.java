package oolala.view.applicationViews;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import oolala.model.OolalaModels.OolalaModel;
import oolala.model.OolalaModels.OolalaModelLSystem;
import oolala.model.turtle.TurtleMethod;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class LSystemViewTest extends DukeApplicationTest {

  private TextArea textArea;
  private Button runCode;

  @Override
  public void start(Stage stage) {
    OolalaModel model = new OolalaModelLSystem("English");
    OolalaView view = new LSystemView(model, "English", 1000, 800);
    stage.setScene(view.makeScene());
    stage.show();
    textArea = lookup("#CodeInput").query();
    runCode = lookup("#RunCode").query();
  }

  @Test
  void GIVEN_distanceSliderValue_WHEN_moveCalled_THEN_moveSliderDistance() {
    int expected = 15;
    Slider slider = lookup("#MoveDistanceSlider").query();
    setValue(slider, expected);
    writeInputTo(textArea, "Start : F\nRule F F");
    clickOn(runCode);
    sleep(1000);
    Line line = lookup("#Line").query();
    assertEquals(line.getStartY() - line.getEndY(), expected);
  }

  @Test
  void GIVEN_turnSliderValue_WHEN_turnCalled_THEN_turnSliderDegrees() {
    Slider slider = lookup("#TurnDegreeSlider").query();
    setValue(slider, 90);
    Slider loop = lookup("#LevelCountSlider").query();
    setValue(loop, 1);
    writeInputTo(textArea, "Start : F\nRule F +F");
    clickOn(runCode);
    sleep(1000);
    Line line = lookup("#Line").query();
    assertEquals(line.getStartY() - line.getEndY(), 0);
  }

  @Test
  void GIVEN_levelCount_WHEN_runCodeCalled_THEN_runLevel() {
    Slider slider = lookup("#LevelCountSlider").query();
    setValue(slider, 3);
    writeInputTo(textArea, "Start : W\nRule W X\nRule X Y\nRule Y F\nRule F +");
    clickOn(runCode);
    sleep(1000);
    Line line = lookup("#Line").query();
    assertEquals(line.getStartY() - line.getEndY(), 10);
  }

  @Test
  void GIVEN_forward_WHEN_historySelected_THEN_moveForward() {
    Slider slider = lookup("#LevelCountSlider").query();
    setValue(slider, 0);
    writeInputTo(textArea, "Start : F");
    clickOn(runCode);
    sleep(1000);
    ComboBox<TurtleMethod> history = lookup("#History").query();
    select(history, new TurtleMethod("FD", new ArrayList<>(List.of(10))));
    sleep(1000);
    ImageView turtle = lookup("#TurtleImage").query();
    assertEquals(turtle.getY(), 255);
  }

  @Test
  void testComboChoices() {
    ComboBox<String> images = lookup("#ImageComboBox").query();
    String expected = "Set Image";
    assertEquals(images.getValue(), expected);
    // GIVEN, app first starts up
    // WHEN, choice is made using a combo box
    expected = "black.png";
    select(images, expected);
    // THEN, check label text has been updated to match input
    assertEquals(expected, images.getValue());
  }

  @Test
  void testTextFieldAction() {
    VBox textBox = lookup("#SetLocation").query();
    TextField textField = (TextField) textBox.getChildren().get(1);
    String expected = "(325,300)";
    assertEquals(textField.getText(), expected);
    // GIVEN, app first starts up
    // WHEN, text is typed and action is activated with ENTER key
    expected = "(1,1)";
    textField.setText(expected);
    // THEN, check label text has been updated to match input
    assertEquals(textField.getText(), expected);
  }

  @Test
  void GIVEN_inputtedValues_WHEN_clearEnvironmentPressed_THEN_resetValues() {
    writeInputTo(textArea, "Start : F\nRule F F");
    Slider slider = lookup("#MoveDistanceSlider").query();
    setValue(slider, 15);
    clickOn(runCode);
    Button clearButton = lookup("#ClearEnvironment").query();
    clickOn(clearButton);
    TextArea ta = lookup("#CodeInput").query();
    assertEquals(ta.getText(), "");
    Slider newSlider = lookup("#MoveDistanceSlider").query();
    assertEquals(newSlider.getValue(), 10);
    Slider degreeSlider = lookup("#TurnDegreeSlider").query();
    assertEquals(degreeSlider.getValue(),30);
  }

}
