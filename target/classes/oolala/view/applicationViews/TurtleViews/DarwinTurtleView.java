package oolala.view.applicationViews.TurtleViews;

import java.util.Map;
import javafx.scene.image.Image;
import oolala.model.turtle.DarwinTurtleModel;
import oolala.model.turtle.TurtleModel;
import oolala.util.Bean;

/**
 * This class is the turtle view for a Darwin Simulator.  It has an added species change listener
 * and sets its image to a map from species to image.
 *
 * @author Nolan Burroughs
 */
public class DarwinTurtleView extends TurtleView {

  private int mySpecies;
  private Map<Integer, String> mySpeciesMap;

  /**
   * Constructs a Darwin Turtle with the image set to the current mapping of its starting species.
   *
   * @param myTurtleModel Given turtle model
   * @param map           Initial map of species to image
   */
  public DarwinTurtleView(TurtleModel myTurtleModel, Map<Integer, String> map) {
    super(myTurtleModel);
    DarwinTurtleModel model = (DarwinTurtleModel) myTurtleModel;
    mySpecies = model.getSpecies();
    mySpeciesMap = map;
    setSpecies(mySpecies);
    for (Bean bean : myTurtleModel.getBeans()) {
      if (bean.getType().equals("Species")) {
        bean.addPropertyChangeListener(e -> setSpecies((int) e.getNewValue()));
      }
    }
  }

  /**
   * Updates the species map and updates the image of the turtle for the new mapping.
   *
   * @param speciesMap New species to image map
   */
  public void setSpeciesMap(Map<Integer, String> speciesMap) {
    mySpeciesMap = speciesMap;
    setSpecies(mySpecies);
  }

  // Sets the species of the turtle and updates its species
  private void setSpecies(int species) {
    mySpecies = species;
    myTurtle.setImage(new Image(mySpeciesMap.get(mySpecies)));
  }
}
