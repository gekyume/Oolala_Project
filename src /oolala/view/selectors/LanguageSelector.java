package oolala.view.selectors;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oolala.view.selectors.languageButtons.EnglishButton;
import oolala.view.selectors.languageButtons.FrenchButton;
import oolala.view.selectors.languageButtons.SpanishButton;

import java.util.Objects;

/**
 * This is the view for selecting the wanted language.
 *
 * @author Nolan Burroughs
 */
public class LanguageSelector {

  private Stage myStage;
  private Group root;
  private int myWidth;
  private int myHeight;
  public static final String DEFAULT_RESOURCE_PACKAGE = "oolala.view.resources.";
  public static final String DEFAULT_STYLESHEET =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/") + "Default.css";

  /**
   * Constructs a selector for implemented languages.
   *
   * @param stage  Current stage
   * @param width  Scene width
   * @param height Scene height
   */
  public LanguageSelector(Stage stage, int width, int height) {
    myStage = stage;
    myWidth = width;
    myHeight = height;
    root = new Group(makeLanguageButtons(), makeText());
    root.setId("LanguageRoot");
  }

  /**
   * @return Scene containing the language selection buttons
   */
  public Scene setScene() {
    return new Scene(root, myWidth, myHeight);
  }

  // Makes a language selection button for each implemented language
  private HBox makeLanguageButtons() {
    HBox LanguageButtons = new HBox();
    LanguageButtons.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());

    EnglishButton englishButton = new EnglishButton(myWidth, myHeight);
    LanguageButtons.getChildren().add(englishButton.makeButton(myStage));
    SpanishButton spanishButton = new SpanishButton(myWidth, myHeight);
    LanguageButtons.getChildren().add(spanishButton.makeButton(myStage));
    FrenchButton frenchButton = new FrenchButton(myWidth, myHeight);
    LanguageButtons.getChildren().add(frenchButton.makeButton(myStage));
    LanguageButtons.setId("languageButtons");

    LanguageButtons.setTranslateX(150);
    LanguageButtons.setTranslateY(350);
    return LanguageButtons;
  }

  // Makes the text prompting the user to select a language
  private HBox makeText() {
    HBox LanguageText = new HBox();
    LanguageText.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
    Text chooseLanguage = new Text("Choose Language/Elige Lengua/Choisissez la Langue:");
    chooseLanguage.setId("ChooseLanguageFont");
    LanguageText.getChildren().add(chooseLanguage);
    LanguageText.setTranslateX(50);
    LanguageText.setTranslateY(250);
    return LanguageText;
  }

}
