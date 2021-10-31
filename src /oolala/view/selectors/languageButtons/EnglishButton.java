package oolala.view.selectors.languageButtons;

/**
 * This class represents a language button for English.
 *
 * @author Nolan Burroughs
 */
public class EnglishButton extends LanguageButton {

  public static final String ENGLISH = "English";

  /**
   * Constructs a button with English as the language.
   *
   * @param width  Scene width
   * @param height Scene height
   */
  public EnglishButton(int width, int height) {
    super(ENGLISH, width, height);
  }
}
