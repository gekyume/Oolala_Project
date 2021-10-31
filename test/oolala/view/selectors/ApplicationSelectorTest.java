package oolala.view.selectors;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class ApplicationSelectorTest extends DukeApplicationTest {

  @Override
  public void start(Stage stage) {
    ApplicationSelector applicationSelector = new ApplicationSelector(stage, "English", 1000, 800);
    stage.setScene(applicationSelector.setScene());
    stage.show();
  }

  @Test
  void GIVEN_logo_WHEN_buttonPressed_THEN_showLogoView() {
    Button button = lookup("#LogoProgramming").query();
    clickOn(button);
    Slider slider = lookup("#Slider").query();
    assertEquals(slider.getMax(), 5);
  }

  @Test
  void GIVEN_lSystem_WHEN_buttonPressed_THEN_showLSystemView() {
    Button button = lookup("#LSystem").query();
    clickOn(button);
    Slider slider = lookup("#MoveDistanceSlider").query();
    assertEquals(slider.getMax(), 15);
  }

  @Test
  void GIVEN_darwin_WHEN_buttonPressed_THEN_showDarwinView() {
    Button button = lookup("#Darwin").query();
    clickOn(button);
    Slider slider = lookup("#RadiusSlider").query();
    assertEquals(slider.getMax(), 20);
  }
}