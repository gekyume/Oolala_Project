package oolala.view.applicationViews;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oolala.model.OolalaModels.OolalaModel;
import oolala.model.OolalaModels.OolalaModelLSystem;
import oolala.model.turtle.LogoTurtleModel;
import oolala.view.applicationViews.TurtleScreens.LogoTurtleScreen;

/**
 * This class represents the main view class for LSystem applications.
 *
 * @author Nolan Burroughs
 */
public class LSystemView extends OolalaView {

  public static final String DEFAULT_IMAGE_STRING = "oolala/view/images/%s";
  private final double DEGREE_SLIDER_MINIMUM = -180;
  private final double DEGREE_SLIDER_MAXIMUM = 180;
  private final double DEGREE_SLIDER_INITIAL = 30;
  private final double LEVEL_SLIDER_MINIMUM = 0;
  private final double LEVEL_SLIDER_MAXIMUM = 5;
  private final double LEVEL_SLIDER_INITIAL = 3;
  private final double DISTANCE_SLIDER_MINIMUM = 0;
  private final double DISTANCE_SLIDER_MAXIMUM = 15;
  private final double DISTANCE_SLIDER_INITIAL = 10;

  /**
   * Constructs the main view for LSystem applications.
   *
   * @param oolalaModel Inputted model
   * @param language    Current language
   * @param width       Scene width
   * @param height      Scene height
   */
  public LSystemView(OolalaModel oolalaModel, String language, int width, int height) {
    super(oolalaModel, language, width, height);
    myTurtleScreen = new LogoTurtleScreen(myOolalaModel.getCodeRunner());
  }

  /**
   * @return All the elements unique to this application
   */
  @Override
  protected Node makeApplicationElements() {
    return setId("ApplicationElements",
        new HBox(makeDistanceSlider(), makeDegreeSlider(), makeLevelSlider(),
            makeLocationInputField(),
            makeImageSelectionBox(), makeHistory()));
  }

  /**
   * Makes a slider for distance of movement
   *
   * @return VBox containing MoveDistanceSlider and MoveDistanceText
   */
  private Node makeDistanceSlider() {
    Slider slider = new Slider(DISTANCE_SLIDER_MINIMUM, DISTANCE_SLIDER_MAXIMUM,
        DISTANCE_SLIDER_INITIAL);
    Text penText = new Text(
        String.format("%s: %d", myResources.getString("MoveDistance"), (int) slider.getValue()));
    slider.valueProperty().addListener((ov, t, t1) -> {
      OolalaModelLSystem model = (OolalaModelLSystem) myOolalaModel;
      model.setDistance(t1.intValue());
      penText.setText(
          String.format("%s: %d", myResources.getString("MoveDistance"), t1.intValue()));
    });
    return new VBox(setId("MoveDistanceSlider", slider), setId("MoveDistanceText", penText));
  }

  /**
   * Makes a slider for degree of rotation
   *
   * @return VBox containing TurnDegreeSlider and TurnDegreeText
   */
  private Node makeDegreeSlider() {
    Slider slider = new Slider(DEGREE_SLIDER_MINIMUM, DEGREE_SLIDER_MAXIMUM, DEGREE_SLIDER_INITIAL);
    Text penText = new Text(
        String.format("%s: %d", myResources.getString("TurnDegree"), (int) slider.getValue()));
    slider.valueProperty().addListener((ov, t, t1) -> {
      OolalaModelLSystem model = (OolalaModelLSystem) myOolalaModel;
      model.setDegree(t1.intValue());
      penText.setText(String.format("%s: %d", myResources.getString("TurnDegree"), t1.intValue()));
    });
    return new VBox(setId("TurnDegreeSlider", slider), setId("TurnDegreeText", penText));
  }

  /**
   * Makes a slider for degree of rotation
   *
   * @return VBox containing LevelCountSlider and LevelCountText
   */
  private Node makeLevelSlider() {
    Slider slider = new Slider(LEVEL_SLIDER_MINIMUM, LEVEL_SLIDER_MAXIMUM, LEVEL_SLIDER_INITIAL);
    Text penText = new Text(
        String.format("%s: %d", myResources.getString("LevelCount"), (int) slider.getValue()));
    slider.valueProperty().addListener((ov, t, t1) -> {
      OolalaModelLSystem model = (OolalaModelLSystem) myOolalaModel;
      model.setLoops(t1.intValue());
      penText.setText(String.format("%s: %d", myResources.getString("LevelCount"), t1.intValue()));
    });
    return new VBox(setId("LevelCountSlider", slider), setId("LevelCountText", penText));
  }

  /**
   * Makes an input field to enter a new home for turtles
   *
   * @return LocationBox
   */
  private Node makeLocationInputField() {
    VBox homeBox = new VBox();
    Text HomeText = new Text();
    HomeText.setText(myResources.getString("EnterLocation") + ":");
    HomeText.setId("LocationText");
    homeBox.getChildren().add(HomeText);
    double[] originalHome = myOolalaModel.getCodeRunner().getHome();
    TextField textField = new TextField();
    textField.setText(String.format("(%d,%d)", (int) originalHome[0], (int) originalHome[1]));
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\((\\d+|),(\\d+|)\\)")) {
        textField.setText(oldValue);
      } else if ((newValue.matches("\\(\\d+,\\d+\\)"))) {
        updateHome(newValue, myOolalaModel.getCodeRunner());
        LogoTurtleModel turtle = (LogoTurtleModel) myOolalaModel.getCodeRunner().getTurtles()
            .get(0);
        turtle.setPenState(false);
        turtle.home();
        turtle.setPenState(true);
      }
    });
    homeBox.getChildren().add(textField);
    return setId("SetLocation", homeBox);
  }

  /**
   * Makes a choice box to select the image of all the turtles
   *
   * @return Node containing ImageComboBox
   */
  private Node makeImageSelectionBox() {
    return makeImageSelector("ImageComboBox",
        (ChangeListener<String>) (ov, t, t1) -> myTurtleScreen.setTurtleImage(
            String.format(DEFAULT_IMAGE_STRING, t1)));
  }

  /**
   * Clears and resets the environment
   */
  @Override
  protected void clearEnvironment() {
    myOolalaModel = new OolalaModelLSystem(myLanguage);
    myTurtleScreen = new LogoTurtleScreen(myOolalaModel.getCodeRunner());
    myScene.setRoot(new VBox(new HBox(myTurtleScreen.getScreenNode(), makeCodeInput()),
        makeApplicationElements()));
  }
}
