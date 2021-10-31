package oolala;

import javafx.application.Application;
import javafx.stage.Stage;
import oolala.view.selectors.LanguageSelector;

import java.awt.*;

/**
 * The main for the oolala compiler project
 *
 * @author Nolan Burroughs
 * @author Casey Goldstein
 * @author Kyle White
 */
public class Main extends Application {

  // convenience constants
  public static final String TITLE = "Language Selector";
  public static final Dimension DEFAULT_SIZE = new Dimension(1000, 800);

  /**
   * @see Application#start(Stage)
   */
  @Override
  public void start(Stage stage) {
    LanguageSelector languageSelector = new LanguageSelector(stage, DEFAULT_SIZE.width,
        DEFAULT_SIZE.height);
    // give the window a title
    stage.setTitle(TITLE);
    // add our user interface components to Frame and show it
    stage.setScene(languageSelector.setScene());
    stage.show();
  }
}
