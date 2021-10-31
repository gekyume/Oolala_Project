package oolala.view.applicationViews;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oolala.model.OolalaModels.OolalaModelLogo;
import oolala.model.turtle.TurtleMethod;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Tests the functionality of the Logo viewer using TestFX
 *
 * @author Nolan Burroughs
 * @author Kyle White
 */
class LogoViewTest extends DukeApplicationTest {

  public static final String DEFAULT_HOME_LOCATION = "Location: (325,300)\nDegree: 0";
  private TextArea codeField;
  private Button runCodeButton;

  @Override
  public void start(Stage stage) {
    OolalaView oolalaView = new LogoView(new OolalaModelLogo("English"), "English", 1000, 800);
    Scene scene = oolalaView.makeScene();
    stage.setScene(scene);
    stage.show();
    codeField = lookup("#CodeInput").query();
    runCodeButton = lookup("#RunCode").query();
  }

  @Test
  void GIVEN_english_WHEN_oolalaViewConstructed_THEN_displayNamesEnglish() {
    Button runCodeButton = lookup("#RunCode").query();
    assertEquals("Run Code", runCodeButton.getText());
    Button importFileButton = lookup("#ImportFile").query();
    assertEquals("Import File", importFileButton.getText());
  }

  @Test
  void GIVEN_turtleScreen_WHEN_colorPickerColorSelected_THEN_changeScreenDisplayColor() {
    HBox pickerBox = lookup("#ScreenPicker").query();
    ColorPicker picker = (ColorPicker) pickerBox.getChildren().get(1);
    Color expected = Color.RED;
    Rectangle screen = lookup("#Screen").query();
    assertEquals(screen.getFill(), Color.WHITE);
    setValue(picker, expected);
    assertEquals(screen.getFill(), expected);
  }

  @Test
  void GIVEN_manyCommands_WHEN_runCodePressed_THEN_doNotThrowErrors() {
    writeInputTo(codeField, "fD 10 bk 22 Lt 3 rT 72 TELL 1 2 3 Pu pd stamp\n home sT hT");
    clickOn(runCodeButton);
  }

  @Test
  void GIVEN_illegalCommand_WHEN_runCodePressed_THEN_showIncorrectCommandWarning() {
    writeInputTo(codeField, "f");
    clickOn(runCodeButton);
    assertEquals(getDialogMessage(), "Incorrect Command: f");
  }

  @Test
  void GIVEN_missingParameter_WHEN_runCodePressed_THEN_showMissingCommandArgumentWarning() {
    writeInputTo(codeField, "rt");
    clickOn(runCodeButton);
    assertEquals(getDialogMessage(), "Missing Command Argument: rt");
  }

  @Test
  void GIVEN_noText_WHEN_runCodePressed_THEN_doNothing() {
    clickOn(runCodeButton);
    assertEquals(codeField.getText(), "");
  }

  @Test
  void GIVEN_pink_WHEN_penColorPickerPressed_THEN_drawPinkLines() {
    HBox pickerBox = lookup("#PenPicker").query();
    ColorPicker cp = (ColorPicker) pickerBox.getChildren().get(1);
    setValue(cp, Color.PINK);
    writeInputTo(codeField, "fd 50");
    clickOn(runCodeButton);
    sleep(1000);
    Line line = lookup("#Line").query();
    assertEquals(line.getStroke(), Color.PINK);
  }

  @Test
  void GIVEN_forward_WHEN_historySelected_THEN_moveForward() {
    writeInputTo(codeField, "pu\nfd 50\npd");
    clickOn(runCodeButton);
    sleep(1000);
    ComboBox<TurtleMethod> history = lookup("#History").query();
    select(history, new TurtleMethod("FD", new ArrayList<>(
        List.of(50))));
    sleep(1000);
    Line line = lookup("#Line").query();
    assertEquals(line.getEndY(), 200);
  }

  @Test
  void testSliderAction() {
    Slider slider = lookup("#Slider").query();
    Text text = lookup("#PenText").query();
    String expected = "Pen Width: 5.00";
    // GIVEN, app first starts up
    // WHEN, slider is used
    setValue(slider, 5);
    // THEN, check Slider and Text Field has been updated to match input
    assertEquals(slider.getValue(), 5);
    assertEquals(text.getText(), expected);
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
    VBox homeBox = lookup("#SetHome").query();
    TextField textField = (TextField) homeBox.getChildren().get(1);
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
  void testUpdateTextActionRT() {
    Text text = lookup("#InfoText").query();
    assertEquals(text.getText(), DEFAULT_HOME_LOCATION);
    // GIVEN, app first starts up
    // WHEN, text is typed and action is activated with ENTER key
    String expected = "Location: (325,300)\nDegree: 10";
    clickOn(codeField).write("rt 10").write(KeyCode.ENTER.getChar());
    clickOn(runCodeButton);
    // THEN, check label text has been updated to match input
    sleep(1000);
    assertEquals(text.getText(), expected);
  }

  @Test
  void testUpdateTextActionFD() {
    Text text = lookup("#InfoText").query();
    assertEquals(text.getText(), DEFAULT_HOME_LOCATION);
    // GIVEN, app first starts up
    // WHEN, text is typed and action is activated with ENTER key
    String expected = "Location: (325,200)\nDegree: 0";
    clickOn(codeField).write("fd 100").write(KeyCode.ENTER.getChar());
    clickOn(runCodeButton);
    // THEN, check label text has been updated to match input
    sleep(1000);
    assertEquals(text.getText(), expected);
  }

  @Test
  void testUpdateTextActionHome() {
    Text text = lookup("#InfoText").query();
    assertEquals(text.getText(), DEFAULT_HOME_LOCATION);
    // GIVEN, app first starts up
    // WHEN, text is typed and action is activated with ENTER key
    clickOn(codeField).write("fd 100").write(KeyCode.ENTER.getChar());
    clickOn(runCodeButton);
    codeField.clear();
    clickOn(codeField).write("Home").write(KeyCode.ENTER.getChar());
    clickOn(runCodeButton);
    // THEN, check label text has been updated to match input
    sleep(1000);
    assertEquals(text.getText(), DEFAULT_HOME_LOCATION);
  }

  @Test
  void testUpdateTextActionTell() {
    Text text = lookup("#InfoText").query();
    assertEquals(text.getText(), DEFAULT_HOME_LOCATION);
    // GIVEN, app first starts up
    // WHEN, text is typed and action is activated with ENTER key
    String expected = "Location: (325,300)\nDegree: 0\nLocation: (325,300)\nDegree: 0";
    clickOn(codeField).write("Tell 1 2").write(KeyCode.ENTER.getChar());
    clickOn(runCodeButton);
    // THEN, check label text has been updated to match input
    sleep(1000);
    assertEquals(text.getText(), expected);
  }

  @Test
  void testClearEnvironment() {
    writeInputTo(codeField, "FD 10");
    HBox penBox = lookup("#PenPicker").query();
    ColorPicker cp = (ColorPicker) penBox.getChildren().get(1);
    setValue(cp, Color.RED);
    Button clearButton = lookup("#ClearEnvironment").query();
    clickOn(runCodeButton);
    clickOn(clearButton);
    TextArea ta = lookup("#CodeInput").query();
    assertEquals(ta.getText(), "");
    HBox newPenBox = lookup("#PenPicker").query();
    ColorPicker newCp = (ColorPicker) newPenBox.getChildren().get(1);
    assertEquals(newCp.getValue(), Color.BLACK);
  }
}