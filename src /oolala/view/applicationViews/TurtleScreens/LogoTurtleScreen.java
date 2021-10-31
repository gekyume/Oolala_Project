package oolala.view.applicationViews.TurtleScreens;

import oolala.model.codeRunners.CodeRunner;
import oolala.model.turtle.LogoTurtleModel;
import oolala.view.applicationViews.TurtleViews.LogoTurtleView;

/**
 * This class represents the screen for a Logo simulation.
 *
 * @author Casey Goldstein
 */

public class LogoTurtleScreen extends TurtleScreen {

  /**
   * Constructs a Logo screen with LogoTurtleViews
   *
   * @param codeRunner Code runner to get bean from
   */
  public LogoTurtleScreen(CodeRunner codeRunner) {
    super(codeRunner);

    LogoTurtleModel turtleModel = (LogoTurtleModel) codeRunner.getTurtles().get(0);
    turtleModel.home();

    LogoTurtleView turtle = new LogoTurtleView(turtleModel);
    myTurtles.add(turtle);
    myScreenNode.getChildren().add(turtle.getDisplayNode());
    codeRunner.getBean().addPropertyChangeListener(e -> {
      LogoTurtleView newTurtle = new LogoTurtleView((LogoTurtleModel) e.getNewValue());
      myScreenNode.getChildren().add(newTurtle.getDisplayNode());
      myTurtles.add(newTurtle);
    });
  }

}
