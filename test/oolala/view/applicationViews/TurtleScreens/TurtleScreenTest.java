package oolala.view.applicationViews.TurtleScreens;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import oolala.model.codeRunners.LogoCodeRunner;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class TurtleScreenTest extends DukeApplicationTest {

  private static final int WIDTH = 650;
  private static final int HEIGHT = 600;
  private Rectangle screen;
  private LogoTurtleScreen turtleScreen;

  @Override
  public void start(Stage stage) {
    turtleScreen = new LogoTurtleScreen(new LogoCodeRunner());
    Group root = new Group(turtleScreen.getScreenNode());
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    stage.setScene(scene);
    stage.show();
    screen = lookup("#Screen").query();
  }

  @Test
  void GIVEN_classConstructed_WHEN_getScreenNodeCalled_THEN_returnNodeWithScreen() {
    assertEquals(screen.getWidth(), WIDTH);
    assertEquals(screen.getHeight(), HEIGHT);
  }

  @Test
  void GIVEN_color_WHEN_setScreenColorCalled_THEN_setScreenColor() {
    turtleScreen.setScreenColor(Color.GREY);
    assertEquals(screen.getFill(), Color.GREY);
  }
}