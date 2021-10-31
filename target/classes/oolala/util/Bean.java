package oolala.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This class implements bean for a property listener. Based on code found here:
 * http://www.javaquizplayer.com/blogposts/java-propertychangelistener-as-observer-19.html
 *
 * @author Nolan Burroughs
 */
public class Bean {

  protected PropertyChangeSupport mySupport = new PropertyChangeSupport(this);
  protected Object myProperty;
  private final String myType;

  /**
   * Constructs a bean with a given type
   *
   * @param type Type of bean
   */
  public Bean(String type) {
    myType = type;
  }

  /**
   * Adds a property change listener to the property change support
   *
   * @param listener Property change listener to add
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    mySupport.addPropertyChangeListener(listener);
  }

  /**
   * Sets the new property value and fires a property change event
   *
   * @param newProperty New value of the property
   */
  public void setProperty(Object newProperty) {
    Object oldProperty = myProperty;
    myProperty = newProperty;
    mySupport.firePropertyChange(myType, oldProperty, newProperty);
  }

  /**
   * @return The type of bean being followed
   */
  public String getType() {
    return myType;
  }
}
