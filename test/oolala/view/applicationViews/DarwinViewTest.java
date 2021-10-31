package oolala.view.applicationViews;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import oolala.model.OolalaModels.OolalaModel;
import oolala.model.OolalaModels.OolalaModelDarwin;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class DarwinViewTest extends DukeApplicationTest {

  private static final String BLACK = "oolala/view/images/black.png";
  private static final String BLUE = "oolala/view/images/blue.png";
  private static final String YELLOW = "oolala/view/images/yellow.png";
  private TextArea textArea;
  private Button runCode;
  private Button button0;
  private Button button1;
  private Button button2;
  private Button button3;
  private Button addSpecies;

  @Override
  public void start(Stage stage) {
    OolalaModel model = new OolalaModelDarwin("English");
    OolalaView view = new DarwinView(model, "English", 1000, 800);
    stage.setScene(view.makeScene());
    stage.show();
    textArea = lookup("#CodeInput").query();
    runCode = lookup("#RunCode").query();
    button0 = lookup("#Species0").query();
    button1 = lookup("#Species1").query();
    button2 = lookup("#Species2").query();
    button3 = lookup("#Species3").query();
    addSpecies = lookup("#AddTurtle").query();
  }

  @Test
  void GIVEN_text_WHEN_speciesSelected_THEN_loadText() {
    String expected = "cantaloupe";
    writeInputTo(textArea, expected);
    clickOn(button1);
    assertEquals(textArea.getText(), "");
    clickOn(button0);
    assertEquals(textArea.getText(), expected);
  }

  @Test
  void GIVEN_multipleSpecies_WHEN_runCodeSelected_THEN_addAllSpeciesCode() {
    writeInputTo(textArea, "RIGHT 90\nGO 1");
    clickOn(addSpecies);
    clickOn(button1);
    writeInputTo(textArea, "Left 90\nGo 1");
    clickOn(addSpecies);
    clickOn(button2);
    writeInputTo(textArea, "MOVE 1\nGo 1");
    clickOn(addSpecies);
    clickOn(button3);
    writeInputTo(textArea, "MOVE 2\nGo 1");
    clickOn(addSpecies);
    clickOn(runCode);
    sleep(2000);
  }

  @Test
  void GIVEN_missingParameter_WHEN_runCode_THEN_throwCorrectError() {
    writeInputTo(textArea, "RIGHT");
    clickOn(runCode);
    assertEquals(getDialogMessage(), "Missing Command Argument: RIGHT");
  }

  @Test
  void GIVEN_noCodeForSpecies_WHEN_runCode_THEN_throwCorrectError() {
    clickOn(addSpecies);
    clickOn(runCode);
    sleep(1000);
    assertEquals(getDialogMessage(), "Species 0 missing code");
  }

  @Test
  void GIVEN_paused_WHEN_pauseButtonPressed_THEN_displayPlay() {
    Button button = lookup("#Pause").query();
    clickOn(button);
    assertEquals(button.getText(), "Play");
  }

  @Test
  void GIVEN_paused_WHEN_playButtonPressed_THEN_displayPause() {
    Button button = lookup("#Pause").query();
    clickOn(button);
    clickOn(button);
    assertEquals(button.getText(), "Pause");
  }

  @Test
  void GIVEN_existingTurtle_WHEN_speciesColorChanged_THEN_updateImage() {
    clickOn(addSpecies);
    ImageView image = lookup("#TurtleImage").query();
    ComboBox<String> box = lookup("#SpeciesImageBox").query();
    select(box, "blue.png");
    assertTrue(checkImageEquality(image.getImage(), new Image(BLUE)));
  }

  @Test
  void GIVEN_newTurtle_WHEN_speciesColorChanged_THEN_createWithCorrectImage() {
    ComboBox<String> box = lookup("#SpeciesImageBox").query();
    select(box, "yellow.png");
    clickOn(addSpecies);
    ImageView image = lookup("#TurtleImage").query();
    assertTrue(checkImageEquality(image.getImage(), new Image(YELLOW)));
  }

  @Test
  void GIVEN_turtleAddedToRadius_WHEN_radiusIncreased_THEN_infectIt() {
    clickOn(addSpecies);
    ImageView image = lookup("#TurtleImage").query();
    writeInputTo(textArea, "RIGHT 90\nGO 1");
    clickOn(button1);
    TextField textField = lookup("#NewTurtleLocation").query();
    textField.setText("(325,306)");
    clickOn(addSpecies);
    writeInputTo(textArea, "INFECT\nGO 1");
    clickOn(runCode);
    sleep(1000);
    assertTrue(checkImageEquality(image.getImage(), new Image(BLACK)));
    Slider slider = lookup("#RadiusSlider").query();
    setValue(slider, 7);
    sleep(1000);
    assertTrue(checkImageEquality(image.getImage(), new Image(BLUE)));
  }

  @Test
  void GIVEN_inputtedValues_WHEN_clearEnvironmentPressed_THEN_resetValues() {
    writeInputTo(textArea, "MOVE 10\nGO 1");
    Slider slider = lookup("#RadiusSlider").query();
    setValue(slider, 7);
    clickOn(runCode);
    Button clearButton = lookup("#ClearEnvironment").query();
    clickOn(clearButton);
    TextArea ta = lookup("#CodeInput").query();
    assertEquals(ta.getText(), "");
    Slider newSlider = lookup("#RadiusSlider").query();
    assertEquals(newSlider.getValue(), 5);
  }

  private boolean checkImageEquality(Image image1, Image image2) {
    for (int i = 0; i < image1.getWidth(); i++) {
      for (int j = 0; j < image1.getHeight(); j++) {
        if (!image1.getPixelReader().getColor(i, j)
            .equals(image2.getPixelReader().getColor(i, j))) {
          return false;
        }
      }
    }
    return true;
  }
}