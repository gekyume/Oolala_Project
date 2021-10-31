package oolala.view.applicationViews;

import static oolala.view.applicationViews.LogoView.DEFAULT_IMAGE_PACKAGE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import oolala.model.OolalaModels.OolalaModel;
import oolala.model.codeRunners.CodeRunner;
import oolala.model.turtle.TurtleMethod;
import oolala.view.applicationViews.TurtleScreens.TurtleScreen;

/**
 * This class represents the main view object for the oolala compiler
 *
 * @author Nolan Burroughs
 * @author Casey Goldstein
 * @author Kyle White
 */
public abstract class OolalaView {

  public static final double SECONDS_DELAY = .1;
  public static final String DEFAULT_RESOURCE_PACKAGE = "oolala.view.resources.";
  public static final String DEFAULT_STYLESHEET =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/") + "Default.css";
  protected int myWidth;
  protected int myHeight;
  protected OolalaModel myOolalaModel;
  protected Timeline myAnimation;
  protected ResourceBundle myResources;
  protected Scene myScene;
  protected TurtleScreen myTurtleScreen;
  protected TextArea myCodeArea;
  protected String myLanguage;
  private ComboBox<TurtleMethod> myHistory;

  /**
   * Constructs the main view object for the oolala class
   *
   * @param oolalaModel The main model object for the oolala class
   */
  public OolalaView(OolalaModel oolalaModel, String language, int width, int height) {
    myOolalaModel = oolalaModel;
    myAnimation = new Timeline();
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    myWidth = width;
    myHeight = height;
    myLanguage = language;
  }

  /**
   * Creates the scene for the oolala view
   *
   * @return The created scene
   */
  public Scene makeScene() {
    Node codeView = new HBox(myTurtleScreen.getScreenNode(), makeCodeInput());
    VBox root = new VBox(codeView, makeApplicationElements());
    myScene = new Scene(root, myWidth, myHeight);
    myScene.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
    return myScene;
  }

  /**
   * Makes the elements unique to each application
   *
   * @return Node containing respective application elements
   */
  protected abstract Node makeApplicationElements();

  /**
   * Makes the entire turtle screen and input buttons
   *
   * @return Node containing CodeInputs VBox
   */
  protected Node makeCodeInput() {
    VBox codeInputs = new VBox();
    codeInputs.setId("CodeInputs");
    myCodeArea = new TextArea();
    codeInputs.getChildren().add(setId("CodeInput", myCodeArea));
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
    Node button = makeButton("ImportFile", event -> inputFile(myCodeArea, fileChooser));
    Node saveButton = makeButton("SaveFile", event -> saveFile(myCodeArea, fileChooser));
    Node runButton = makeButton("RunCode", event -> runCode());
    codeInputs.getChildren().add(setId("EnvButtons", new HBox(button, saveButton, runButton)));
    Node ScreenColorPicker = makeScreenColorPicker("ScreenPicker", myTurtleScreen);
    codeInputs.getChildren().add(ScreenColorPicker);
    Node PenColorPicker = makePenColorPicker("PenPicker");
    codeInputs.getChildren().add(setId("PenPicker", PenColorPicker));
    Node clearButton = makeButton("ClearEnvironment", event -> clearEnvironment());
    codeInputs.getChildren().add(clearButton);
    return setId("CodeInputs", codeInputs);
  }

  protected abstract void clearEnvironment();

  /**
   * Inputs a file into a text area if one is chosen, does nothing otherwise
   *
   * @param ta          textarea
   * @param fileChooser
   */
  private void inputFile(TextArea ta, FileChooser fileChooser) {
    try {
      File file = fileChooser.showOpenDialog(myScene.getWindow());
      if (file != null) {
        ta.setText(
            Files.readString(
                file.getAbsoluteFile().toPath()));
      }
    } catch (IOException e) {
      showError(e);
    }
  }

  // Saves the text areas content to a file if one is chosen, does nothing otherwise
  private void saveFile(TextArea ta, FileChooser fileChooser) {
    try {
      File file = fileChooser.showSaveDialog(myScene.getWindow());
      if (file != null) {
        FileWriter writer = new FileWriter(file);
        writer.write(ta.getText());
        writer.close();
      }
    } catch (IOException e) {
      showError(e);
    }
  }

  // Begins animation timeline for new code
  private void runCode() {
    myOolalaModel.getCodeRunner().clearCode();
    try {
      parseCode();
    } catch (Exception e) {
      showError(e);
      return;
    }

    if (myAnimation != null) {
      myAnimation.stop();
    }
    if (myHistory != null) {
      myHistory.getItems().addAll(myOolalaModel.getHistory());
    }
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECONDS_DELAY), e -> runLine()));
    myAnimation.play();
  }

  /**
   * Default implementation of parse code.  Just asks the model to parse the current text area.
   */
  protected void parseCode() {
    myOolalaModel.parseBlock(myCodeArea.getText());
  }

  // Calls a method mapped to a single line of code (if possible)
  private void runLine() {
    boolean stop = true;
    try {
      stop = myOolalaModel.getCodeRunner().runLine();
    } catch (Exception e) {
      myAnimation.stop();
      showError(e);
    }
    if (stop) {
      myAnimation.stop();
    }
    updateInfoText();
  }

  // Update information text
  protected void updateInfoText() {
  }

  // Default method to make a combobox of current history of commands to call on selection
  protected Node makeHistory() {
    myHistory = new ComboBox<>();
    myHistory.setOnAction(event -> {
      try {
        myOolalaModel.getCodeRunner().runLine(myHistory.getValue());
      } catch (Exception e) {
        myAnimation.stop();
        showError(e);
      }
    });
    return setId("History", myHistory);
  }

  // Display a thrown error
  private void showError(Exception e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(e.getMessage());
    alert.show();
  }

  // Updates the home of the code runner
  protected static void updateHome(String newValue, CodeRunner codeRunner) {
    String[] coordinates = newValue.split(",");
    double x = Double.parseDouble(coordinates[0].substring(1));
    double y = Double.parseDouble(coordinates[1].substring(0, coordinates[1].length() - 1));
    double[] home = new double[]{x, y};
    codeRunner.setHome(home);
  }

  // Makes am image selector of stored images
  protected Node makeImageSelector(String name, ChangeListener listener) {
    ComboBox<String> imageComboBox = new ComboBox<>();
    File folder = new File(DEFAULT_IMAGE_PACKAGE);
    File[] listOfFiles = folder.listFiles();
    for (File f : listOfFiles) {
      imageComboBox.getItems().add(f.getName());
    }
    imageComboBox.setValue(myResources.getString("SetImage"));
    imageComboBox.valueProperty().addListener(listener);
    return setId(name, imageComboBox);
  }

  // Makes a button with the given text, event handler, and ID
  protected Node makeButton(String name, EventHandler<ActionEvent> onClick) {
    Button button = new Button();
    button.setText(myResources.getString(name));
    button.setOnAction(onClick);
    return setId(name, button);
  }

  // Makes a color picker that sets the screen color of the given object and ID
  protected Node makeScreenColorPicker(String name, TurtleScreen turtleScreen) {
    HBox colorPickerBox = new HBox();
    Text colorPickerText = new Text();
    colorPickerText.setText(myResources.getString(name) + ":");
    colorPickerBox.getChildren().add(colorPickerText);
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setOnAction(event -> turtleScreen.setScreenColor(colorPicker.getValue()));
    colorPickerBox.getChildren().add(colorPicker);
    return setId(name, colorPickerBox);
  }

  // Makes a color picker to set the pen color of the turtles
  protected Node makePenColorPicker(String name) {
    HBox colorPickerBox = new HBox();
    Text colorPickerText = new Text();

    colorPickerText.setText(myResources.getString(name) + ":");
    colorPickerBox.getChildren().add(colorPickerText);
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setValue(Color.BLACK);
    colorPicker.setOnAction(e -> myTurtleScreen.setPenColor(colorPicker.getValue()));
    colorPickerBox.getChildren().add(colorPicker);
    return setId(name, colorPickerBox);
  }

  // Sets the css id of the given node
  protected Node setId(String id, Node node) {
    node.setId(id);
    return node;
  }
}
