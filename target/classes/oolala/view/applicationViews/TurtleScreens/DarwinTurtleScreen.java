package oolala.view.applicationViews.TurtleScreens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oolala.model.codeRunners.CodeRunner;
import oolala.model.turtle.TurtleModel;
import oolala.view.applicationViews.TurtleViews.DarwinTurtleView;
import oolala.view.applicationViews.TurtleViews.TurtleView;

/**
 * This class represents the screen for a Darwin simulation.
 *
 * @author Nolan Burroughs
 */
public class DarwinTurtleScreen extends TurtleScreen {

  private static final int MAX_SPECIES = 4;
  private static final List<String> DEFAULT_IMAGES = new ArrayList<>(
      List.of("oolala/view/images/black.png", "oolala/view/images/blue.png",
          "oolala/view/images/green.png", "oolala/view/images/orange.png"));
  private Map<Integer, String> speciesMap;

  /**
   * Constructs a Darwin simulation screen with Darwin Turtle views.
   *
   * @param codeRunner Inputted code runner
   */
  public DarwinTurtleScreen(CodeRunner codeRunner) {
    super(codeRunner);
    speciesMap = new HashMap<>();
    for (int i = 0; i < MAX_SPECIES; i++) {
      speciesMap.put(i, DEFAULT_IMAGES.get(i));
    }
    codeRunner.getBean().addPropertyChangeListener(e -> {
      TurtleView newTurtle = new DarwinTurtleView((TurtleModel) e.getNewValue(), speciesMap);
      myScreenNode.getChildren().add(newTurtle.getDisplayNode());
      myTurtles.add(newTurtle);
    });
  }

  /**
   * Sets a species mapping to a new image and updates all the turtles of the new mapping.
   *
   * @param species Species mapping
   * @param image   New image
   */
  public void setSpeciesImage(int species, String image) {
    speciesMap.put(species, image);
    for (TurtleView turtle : myTurtles) {
      DarwinTurtleView darwinTurtle = (DarwinTurtleView) turtle;
      darwinTurtle.setSpeciesMap(speciesMap);
    }
  }
}
