package oolala.view.selectors.applicationButtons;

import oolala.model.OolalaModels.OolalaModel;
import oolala.model.OolalaModels.OolalaModelLSystem;
import oolala.view.applicationViews.LSystemView;

/**
 * This class represents an application button that when selected opens an LSystem view.
 *
 * @author Nolan Burroughs
 */
public class LSystemButton extends ApplicationButton {

  public static final String LSYSTEM = "LSystem";

  /**
   * Constructs a button with a LogoView and LogoModel
   *
   * @param language Current language
   * @param width    Scene width
   * @param height   Scene height
   */
  public LSystemButton(String language, int width, int height) {
    super(LSYSTEM, language);
    OolalaModel oolalaModel = new OolalaModelLSystem(language);
    oolalaView = new LSystemView(oolalaModel, language, width, height);
  }
}
