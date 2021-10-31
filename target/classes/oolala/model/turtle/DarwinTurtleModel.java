package oolala.model.turtle;

import oolala.util.Bean;

/**
 * This class represents the turtle graphics model for a darwin simulator.  It contains an extra
 * value and bean to keep track of species, and its pen state is always false.
 *
 * @author Nolan Burroughs
 */
public class DarwinTurtleModel extends TurtleModel {

  private int mySpecies;
  private final Bean mySpeciesBean;

  /**
   * Constructs a darwin turtle at a given location with a given starting species.
   *
   * @param homeTuple       Starting location
   * @param startingSpecies Starting species
   */
  public DarwinTurtleModel(double[] homeTuple, int startingSpecies) {
    super(homeTuple, false);
    mySpecies = startingSpecies;
    myPenState = false;
    mySpeciesBean = addBean("Species", startingSpecies);
  }

  /**
   * @return The current species
   */
  public int getSpecies() {
    return mySpecies;
  }

  /**
   * @param species The new species
   */
  public void setSpecies(int species) {
    mySpecies = species;
    mySpeciesBean.setProperty(mySpecies);
  }
}
