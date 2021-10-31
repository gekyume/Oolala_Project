package oolala.view.selectors.applicationButtons;

import oolala.model.OolalaModels.OolalaModel;
import oolala.model.OolalaModels.OolalaModelDarwin;
import oolala.view.applicationViews.DarwinView;

public class DarwinButton extends ApplicationButton {

  public static final String DARWIN = "Darwin";

  /**
   * Constructs a button with a LogoView and LogoModel
   *
   * @param language Current language
   * @param width    Scene width
   * @param height   Scene height
   */
  public DarwinButton(String language, int width, int height) {
    super(DARWIN, language);
    OolalaModel oolalaModel = new OolalaModelDarwin(language);
    oolalaView = new DarwinView(oolalaModel, language, width, height);
  }
}
