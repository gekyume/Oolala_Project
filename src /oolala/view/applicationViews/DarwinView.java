package oolala.view.applicationViews;

import static oolala.view.applicationViews.LogoView.DEFAULT_IMAGE_STRING;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oolala.model.OolalaModels.OolalaModel;
import oolala.model.OolalaModels.OolalaModelDarwin;
import oolala.model.codeRunners.DarwinCodeRunner;
import oolala.view.applicationViews.TurtleScreens.DarwinTurtleScreen;

/**
 * This class is the main view for a Darwin Simulation.  It contains elements needed to make and
 * control a simulation.  Additionally, it iterates through multiple text ares to input code for
 * every species.
 *
 * @author Nolan Burroughs
 */
public class DarwinView extends OolalaView {

  public static final int MAX_SPECIES = 4;
  private boolean isPaused;
  private double[] home;
  private int currentSpecies;
  private Map<Integer, String> myCodes;

  /**
   * Constructs a Darwin view with a Darwin turtle screen.
   *
   * @param model    Inputted model
   * @param language Current language
   * @param width    Scene width
   * @param height   Scene height
   */
  public DarwinView(OolalaModel model, String language, int width, int height) {
    super(model, language, width, height);
    setUp();
  }

  /**
   * Sets up the Darwin environment
   */
  private void setUp() {
    myTurtleScreen = new DarwinTurtleScreen(myOolalaModel.getCodeRunner());
    currentSpecies = 0;
    myCodes = new HashMap<>();
    for (int i = 0; i < MAX_SPECIES; i++) {
      myCodes.put(i, "");
    }
  }

  /**
   * Parses the code for the text areas of each of the species.
   */
  @Override
  protected void parseCode() {
    myCodes.put(currentSpecies, myCodeArea.getText());
    for (int i = 0; i < MAX_SPECIES; i++) {
      myOolalaModel.parseBlock(myCodes.get(i));
    }
  }

  /**
   * @return All the elements unique to this application
   */
  @Override
  protected Node makeApplicationElements() {
    return setId("ApplicationElements",
        new HBox(makeSpeciesSelector(), makeRadiusSlider(), makeAddTurtleDisplay(),
            makeImageSelector(), makePauseButton()));
  }

  /**
   * Makes an array of buttons that when selected set the species being edited for the rest of the
   * UI.
   *
   * @return Node containing the buttons and text
   */
  private Node makeSpeciesSelector() {
    Text speciesText = new Text(
        String.format(myResources.getString("CurrentSpecies"), currentSpecies));
    speciesText.setId("CurrentSpecies");
    HBox buttons = new HBox();
    buttons.setId("SpeciesButtons");
    for (int i = 0; i < MAX_SPECIES; i++) {
      Button button = new Button(String.format("%d", i));
      button.setOnAction(event -> {
        myCodes.put(currentSpecies, myCodeArea.getText());
        currentSpecies = Integer.parseInt(button.getText());
        speciesText.setText(String.format(myResources.getString("CurrentSpecies"), currentSpecies));
        myCodeArea.setText(myCodes.get(currentSpecies));
      });
      button.setId(String.format("Species%d", i));
      buttons.getChildren().add(button);
    }
    return setId("SpeciesSelector", new VBox(speciesText, buttons));
  }

  /**
   * Makes a slider to control the radius of detection for the simulation
   *
   * @return VBox containing slider and pen text
   */
  private Node makeRadiusSlider() {
    Slider radiusSlider = new Slider(1, 20, 5);
    radiusSlider.setId("RadiusSlider");
    Text penText = new Text(
        String.format("%s: %d", myResources.getString("Radius"), (int) radiusSlider.getValue()));
    radiusSlider.valueProperty().addListener((ov, t, t1) -> {
      penText.setText(
          String.format("%s: %d", myResources.getString("Radius"), t1.intValue()));
      DarwinCodeRunner codeRunner = (DarwinCodeRunner) myOolalaModel.getCodeRunner();
      codeRunner.setRadius(t1.intValue());
    });
    return new VBox(radiusSlider, penText);
  }

  /**
   * Makes the instruments needed to add a new turtle of a specific species to the screen
   *
   * @return VBox containing a TextField and the Add Turtle Button
   */
  private Node makeAddTurtleDisplay() {
    home = myOolalaModel.getCodeRunner().getHome();
    TextField textField = new TextField();
    textField.setText(String.format("(%d,%d)", (int) home[0], (int) home[1]));
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\((\\d+|),(\\d+|)\\)")) {
        textField.setText(oldValue);
      } else if ((newValue.matches("\\(\\d+,\\d+\\)"))) {
        String[] coordinates = newValue.split(",");
        double x = Double.parseDouble(coordinates[0].substring(1));
        double y = Double.parseDouble(coordinates[1].substring(0, coordinates[1].length() - 1));
        home = new double[]{x, y};
      }
    });
    textField.setId("NewTurtleLocation");
    Node addTurtleButton = makeButton("AddTurtle", event -> {
      DarwinCodeRunner codeRunner = (DarwinCodeRunner) myOolalaModel.getCodeRunner();
      codeRunner.addTurtle(home, currentSpecies);
    });
    return new VBox(textField, addTurtleButton);
  }

  /**
   * Creates an image selector that maps to current species
   *
   * @return ImageSelector Node
   */
  private Node makeImageSelector() {
    return makeImageSelector("SpeciesImageBox", (ChangeListener<String>) (ov, t, t1) -> {
      DarwinTurtleScreen screen = (DarwinTurtleScreen) myTurtleScreen;
      screen.setSpeciesImage(currentSpecies,
          String.format(DEFAULT_IMAGE_STRING, t1));
    });
  }

  /**
   * Makes a pause/play simulation button
   *
   * @return Node with Pause Button
   */
  private Node makePauseButton() {
    isPaused = false;
    Button pauseButton = new Button(myResources.getString("Pause"));
    pauseButton.setOnAction(event -> {
      if (isPaused) {
        myAnimation.play();
        pauseButton.setText(myResources.getString("Pause"));
      } else {
        myAnimation.pause();
        pauseButton.setText(myResources.getString("Play"));
      }
      isPaused = !isPaused;
    });
    return setId("Pause", pauseButton);
  }

  /**
   * Clears and resets the environment
   */
  @Override
  protected void clearEnvironment() {
    myAnimation.stop();
    myAnimation = new Timeline();
    myOolalaModel = new OolalaModelDarwin(myLanguage);
    setUp();
    myScene.setRoot(new VBox(new HBox(myTurtleScreen.getScreenNode(), makeCodeInput()),
        makeApplicationElements()));
  }
}
