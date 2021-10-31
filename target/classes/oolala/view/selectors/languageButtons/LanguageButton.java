package oolala.view.selectors.languageButtons;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import oolala.view.selectors.ApplicationSelector;

import java.util.Objects;

/**
 * This class represents a button for selecting a language.
 *
 * @author Nolan Burroughs
 */
public abstract class LanguageButton {

  public static final String SELECT_APPLICATION = "ApplicationSelector";
  protected String myLanguage;
  private int myWidth;
  private int myHeight;
  public static final String DEFAULT_RESOURCE_PACKAGE = "oolala.view.resources.";
  public static final String DEFAULT_STYLESHEET =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/") + "Default.css";

  /**
   * Constructs a language button object for a language.
   *
   * @param language Language for the given button
   * @param width    Scene width
   * @param height   Scene height
   */
  public LanguageButton(String language, int width, int height) {
    myLanguage = language;
    myWidth = width;
    myHeight = height;
  }

  /**
   * Makes a button that sets the scene to an application selector.
   *
   * @param stage Current stage
   * @return Constructed button
   */
  public Button makeButton(Stage stage) {
    Button languageButton = new Button(myLanguage);
    languageButton.setOnAction(event -> setApplicationSelector(stage));
    languageButton.setId(myLanguage);
    languageButton.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
    languageButton.setId(myLanguage);
    return languageButton;
  }

  // Sets the stage to an application selector with the chosen language
  private void setApplicationSelector(Stage stage) {
    ApplicationSelector applicationSelector = new ApplicationSelector(stage, myLanguage, myWidth,
        myHeight);
    stage.setScene(applicationSelector.setScene());
    stage.setTitle(SELECT_APPLICATION);
  }


}
