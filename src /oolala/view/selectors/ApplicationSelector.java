package oolala.view.selectors;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oolala.view.selectors.applicationButtons.ApplicationButton;
import oolala.view.selectors.applicationButtons.DarwinButton;
import oolala.view.selectors.applicationButtons.LSystemButton;
import oolala.view.selectors.applicationButtons.LogoButton;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This is the view to select between different applications.
 *
 * @author Nolan Burroughs
 */
public class ApplicationSelector {

  private String myLanguage;
  private VBox root;
  private int myWidth;
  private int myHeight;
  public static final String DEFAULT_RESOURCE_PACKAGE = "oolala.view.resources.";
  public static final String DEFAULT_STYLESHEET =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/") + "Default.css";
  protected ResourceBundle myResources;


  /**
   * Makes a selector that changes the stage to the chosen application.
   *
   * @param stage    Current stage
   * @param language Selected language
   * @param width    Scene width
   * @param height   Scene height
   */
  public ApplicationSelector(Stage stage, String language, int width, int height) {
    myLanguage = language;
    myWidth = width;
    myHeight = height;
    root = new VBox(makeApplicationButtons(stage));
    root.getChildren().add(makeText(language));

  }

  /**
   * @return Scene containing the application buttons
   */
  public Scene setScene() {
    return new Scene(root, myWidth, myHeight);
  }

  // Makes an application button for each application
  private HBox makeApplicationButtons(Stage stage) {
    HBox ApplicationButtons = new HBox();
    ApplicationButtons.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());

    ApplicationButton logoButton = new LogoButton(myLanguage, myWidth, myHeight);
    ApplicationButtons.getChildren().add(logoButton.makeButton(stage));
    ApplicationButton lSystemButton = new LSystemButton(myLanguage, myWidth, myHeight);
    ApplicationButtons.getChildren().add(lSystemButton.makeButton(stage));
    ApplicationButton darwinButton = new DarwinButton(myLanguage, myWidth, myHeight);
    ApplicationButtons.getChildren().add(darwinButton.makeButton(stage));

    ApplicationButtons.setId("applicationButtons");

    ApplicationButtons.setTranslateY(350);

    return ApplicationButtons;
  }

  // Makes the text prompting hte user to select an application
  private HBox makeText(String language) {
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    HBox ApplicationText = new HBox();
    ApplicationText.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
    Text chooseApplication = new Text(myResources.getString("ChooseApplication"));
    chooseApplication.setId("ChooseApplicationFont");
    ApplicationText.getChildren().add(chooseApplication);
    ApplicationText.setTranslateY(200);
    ApplicationText.setId("ApplicationText");
    return ApplicationText;
  }


}
