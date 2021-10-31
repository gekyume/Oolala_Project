package oolala.view.selectors.applicationButtons;

import oolala.model.OolalaModels.OolalaModel;
import oolala.model.OolalaModels.OolalaModelLogo;
import oolala.view.applicationViews.LogoView;

/**
 * This class represents the application button for Logo.
 *
 * @author Nolan Burroughs
 */
public class LogoButton extends ApplicationButton {

  public static final String LOGO = "LogoProgramming";

  /**
   * Constructs a button with a LogoView and LogoModel
   *
   * @param language Current language
   * @param width    Scene width
   * @param height   Scene height
   */
  public LogoButton(String language, int width, int height) {
    super(LOGO, language);
    OolalaModel oolalaModel = new OolalaModelLogo(language);
    oolalaView = new LogoView(oolalaModel, language, width, height);
  }
}
