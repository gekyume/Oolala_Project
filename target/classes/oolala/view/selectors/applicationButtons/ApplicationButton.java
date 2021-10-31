package oolala.view.selectors.applicationButtons;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import oolala.view.applicationViews.OolalaView;

/**
 * This class represents a button to select an application.
 *
 * @author Nolan Burroughs
 */
public abstract class ApplicationButton {

  public static final String DEFAULT_RESOURCE_PACKAGE = "oolala.view.resources.";
  public static final String TITLE = "Oolala";
  protected OolalaView oolalaView;
  private String applicationName;
  private String myLanguage;

  /**
   * Constructs a button to select an application.
   *
   * @param application Application name
   * @param language    Current language
   */
  public ApplicationButton(String application, String language) {
    applicationName = application;
    myLanguage = language;
  }

  /**
   * Constructs a button to set a scene to the given application.
   *
   * @param stage Current stage
   * @return Constructed button
   */
  public Button makeButton(Stage stage) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + myLanguage);
    Button button = new Button(resourceBundle.getString(applicationName));
    button.setOnAction(event -> setupOolalaApplication(stage));
    button.setId(applicationName);
    return button;
  }

  // Sets scene to the chosen application
  private void setupOolalaApplication(Stage stage) {
    stage.setScene(oolalaView.makeScene());
    stage.setTitle(TITLE);
  }
}
