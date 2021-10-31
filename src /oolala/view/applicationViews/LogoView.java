package oolala.view.applicationViews;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oolala.model.OolalaModels.OolalaModel;
import oolala.model.OolalaModels.OolalaModelLogo;
import oolala.model.turtle.TurtleModel;
import oolala.view.applicationViews.TurtleScreens.LogoTurtleScreen;

/**
 * This class represents the main view for Logo applications
 *
 * @author Nolan Burroughs
 * @author Kyle White
 */
public class LogoView extends OolalaView {

  public static final String DEFAULT_IMAGE_PACKAGE = "src/oolala/view/images";
  public static final String DEFAULT_IMAGE_STRING = "oolala/view/images/%s";
  private Text infoText;

  /**
   * Constructs a view for a logo application
   *
   * @param oolalaModel Inputted model
   * @param language    Inputted language
   */
  public LogoView(OolalaModel oolalaModel, String language, int width, int height) {
    super(oolalaModel, language, width, height);
    myTurtleScreen = new LogoTurtleScreen(myOolalaModel.getCodeRunner());
  }

  /**
   * Makes all the logo application elements
   *
   * @return Node containing Info, PenWidthSlider, ImageSelectionBox, HomeInputField, and History
   */
  @Override
  protected Node makeApplicationElements() {
    return setId("ApplicationElements",
        new HBox(makeInfoNode(), makePenWidthSlider(), makeImageSelectionBox(),
            makeHomeInputField(), makeHistory()));
  }

  /**
   * Displays the current location and rotation of all the turtles
   *
   * @return Node containing infoTextBox
   */
  private Node makeInfoNode() {
    TurtleModel originalTurtle = myOolalaModel.getCodeRunner().getTurtles().get(0);
    HBox infoTextBox = new HBox();
    infoText = new Text(
        String.format("%s: (%d,%d)\n%s: %d", myResources.getString("Location"),
            (int) originalTurtle.getX(), (int) originalTurtle.getY(),
            myResources.getString("Degree"), (int) originalTurtle.getDirection()));
    setId("InfoText", infoText);
    setId("InfoTextBox", infoTextBox);
    infoTextBox.getChildren().add(infoText);
    return infoTextBox;

  }

  /**
   * Makes a slider for the width of the turtle's pens
   *
   * @return VBox Node containing slider and penText
   */
  private Node makePenWidthSlider() {
    Slider slider = new Slider(1, 5, 1);
    Text penText = new Text(
        String.format("%s: %.2f", myResources.getString("PenWidth"), slider.getValue()));
    slider.valueProperty().addListener((ov, t, t1) -> {
      myTurtleScreen.setPenThickness((Double) t1);
      penText.setText(String.format("%s: %.2f", myResources.getString("PenWidth"), t1));
    });
    return new VBox(setId("Slider", slider), setId("PenText", penText));
  }

  /**
   * Makes a choice box to select the image of all the turtles
   *
   * @return
   */
  private Node makeImageSelectionBox() {
    return makeImageSelector("ImageComboBox",
        (ChangeListener<String>) (ov, t, t1) -> myTurtleScreen.setTurtleImage(
            String.format(DEFAULT_IMAGE_STRING, t1)));
  }

  /**
   * Makes an input field to enter a new home for turtles
   *
   * @return Node containing HomeField
   */
  private Node makeHomeInputField() {
    VBox homeBox = new VBox();
    Text homeText = new Text();
    homeText.setText(myResources.getString("EnterNewHome") + ":");
    homeText.setId("HomeText");
    homeBox.getChildren().add(homeText);
    double[] originalHome = myOolalaModel.getCodeRunner().getHome();
    TextField homeTextField = new TextField();
    homeTextField.setText(String.format("(%d,%d)", (int) originalHome[0], (int) originalHome[1]));
    homeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\((\\d+|),(\\d+|)\\)")) {
        homeTextField.setText(oldValue);
      } else if ((newValue.matches("\\(\\d+,\\d+\\)"))) {
        updateHome(newValue, myOolalaModel.getCodeRunner());
      }
    });
    homeBox.getChildren().add(homeTextField);
    return setId("SetHome", homeBox);
  }

  /**
   * Updates the info text for the turtles
   */
  @Override
  protected void updateInfoText() {
    List<TurtleModel> turtles = myOolalaModel.getCodeRunner().getTurtles();
    String[] info = new String[turtles.size()];
    for (int i = 0; i < turtles.size(); i++) {
      info[i] = String.format("%s: (%d,%d)\n%s: %d", myResources.getString("Location"),
          (int) turtles.get(i).getX(), (int) turtles.get(i).getY(), myResources.getString("Degree"),
          (int) turtles.get(i).getDirection());
    }
    infoText.setText(String.join("\n", info));
  }

  /**
   * Clears and resets the environment
   */
  @Override
  protected void clearEnvironment() {
    myOolalaModel = new OolalaModelLogo(myLanguage);
    myTurtleScreen = new LogoTurtleScreen(myOolalaModel.getCodeRunner());
    myScene.setRoot(new VBox(new HBox(myTurtleScreen.getScreenNode(), makeCodeInput()),
        makeApplicationElements()));
  }
}
