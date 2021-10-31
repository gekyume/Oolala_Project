package oolala.view.selectors;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class LanguageSelectorTest extends DukeApplicationTest {

  @Override
  public void start(Stage stage) {
    LanguageSelector languageSelector = new LanguageSelector(stage, 1000, 800);
    stage.setScene(languageSelector.setScene());
    stage.show();
  }

  @Test
  void GIVEN_english_WHEN_buttonPressed_THEN_displayApplicationChooserInEnglish() {
    Button button = lookup("#English").query();
    clickOn(button);
    Button logoButton = lookup("#LogoProgramming").query();
    assertEquals(logoButton.getText(), "Logo Programming");
  }

  @Test
  void GIVEN_french_WHEN_buttonPressed_THEN_displayApplicationChooserInFrench() {
    Button button = lookup("#French").query();
    clickOn(button);
    Button logoButton = lookup("#LogoProgramming").query();
    assertEquals(logoButton.getText(), "Programmation de Logos");
  }

  @Test
  void GIVEN_spanish_WHEN_buttonPressed_THEN_displayApplicationChooserInFrench() {
    Button button = lookup("#Spanish").query();
    clickOn(button);
    Button logoButton = lookup("#LSystem").query();
    assertEquals(logoButton.getText(), "L-Sistema");
  }

  @Test
  void GIVEN_english_WHEN_buttonPressed_THEN_displayOolalaInEnglish() {
    Button button = lookup("#English").query();
    clickOn(button);
    Button logoButton = lookup("#LogoProgramming").query();
    clickOn(logoButton);
    Button runCode = lookup("#SaveFile").query();
    assertEquals(runCode.getText(), "Save File");
  }

  @Test
  void GIVEN_spanish_WHEN_buttonPressed_THEN_displayOolalaInSpanish() {
    Button button = lookup("#Spanish").query();
    clickOn(button);
    Button logoButton = lookup("#LogoProgramming").query();
    clickOn(logoButton);
    Button runCode = lookup("#SaveFile").query();
    assertEquals(runCode.getText(), "Guardar el Archivo");
  }

  @Test
  void GIVEN_french_WHEN_buttonPressed_THEN_displayOolalaInFrench() {
    Button button = lookup("#French").query();
    clickOn(button);
    Button logoButton = lookup("#LogoProgramming").query();
    clickOn(logoButton);
    Button runCode = lookup("#SaveFile").query();
    assertEquals(runCode.getText(), "Enregistrer le Fichier");
  }
}