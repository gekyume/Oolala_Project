# OOLALA Design Plan
## Nolan Burroughs (nqb2), Casey Goldstein (cmg95), Kyle White (kjw47)


### Team Roles and Responsibilities
In general, Kyle will be responsible for the parser, Nolan will be
responsible for the main view, input/output, and error display, and
Casey will be responsible for turtle commands (view and model).

### High Level Design
High level, there will be a main model and view class.  The view will
prompt the user for a language at start.  The view will accept files,
code blocks, and lines of code. This code will be sent to the parser
to be validated and translated to common turtle commands.  The user
will then be able to run either a program, lines of code, or step through
code by selecting in the view.  The view will display any errors in code
format.  The turtle commands will be called on the view turtle classes.
The view turtles will use their matching turtle models for any movement calculations.
The model will store the history of commands, which will be displayed by
the view for selection by the user.

### CRC Card Classes

This class's purpose is to control the main view.

|View| |
|---|---|
|Scene makeScene(width, height)|Model|
|void selectLanguage(language) |InputView|
|void runProgram()|TurtleView|
|void runLine()| |
|void displayError(error) | |


This class's purpose is to control the inputs/outputs.

|InputView| |
|---|---|
|void inputFile(file)     |TurtleView|
|void saveToFile(codeBlock)      |Parser |
|void inputCodeBlock(codeBlock)    |Model |
|void inputCodeLine(code) | |
|void updatePenSize(size) | |
|void updateHomeLocation(int,int)| |
|void updateBackgroundColor(color)| |
|void inputParserLanguage(language)| |

This class's purpose is to control the main model.

|Model| |
|---|---|
|void selectParser(parserName)         |View|
|void updateHistory(Collection< Object>      |Parser|
| |TurtleModel|

This class's purpose is to control the model movement and data
of a turtle.

|TurtleModel| |
|---|---|
|void forward(distance)         |TurtleView|
|void back(distance)      ||
|void home() | |
|void lefTurn(angle) | |
|void rightTurn(angle) | |
|boolean isPenDown()    | |
|boolean isVisible() | |

This class's purpose is to control the view of a turtle.

|TurtleView| |
|---|---|
|void forward(distance)         |View|
|void back(distance)      |TurtleModel|
|void home() | |
|void stamp() | |
|void leftTurn(angle) | |
|void rightTurn(angle) | |

This class's purpose is to be the abstract class for a parser.

|Parser| |
|---|---|
|Collection<Collection< Object>> parseFile(file)         |View|
|Collection<Collection< Object>> parseCodeBlock(code)      |TurtleModel|
|Collection< Object> parseLine(codeLine) | |

This class's purpose is to be a parser for LSystem code.

|LSystemParser| |
|---|---|
|Collection<Collection< Object>> parseFile(file)         |View|
|Collection<Collection< Object>> parseCodeBlock(code)      |TurtleModel|
|Collection< Object> parseLine(codeLine) | |

This class's purpose is to be a parser for Logo Program code.

|LogoProgramParser| |
|---|---|
|Collection<Collection< Object>> parseFile(file) |View|
|Collection<Collection< Object>> parseCodeBlock(code) |TurtleModel|
|Collection< Object> parseLine(codeLine)| |

This class's purpose is to be a parser for Darwin Simulator code.

|DarwinSimulatorParser| |
|---|---|
|Collection<Collection< Object>> parseFile(file) |View|
|Collection<Collection< Object>> parseCodeBlock(code) |TurtleModel|
|Collection< Object> parseLine(codeLine)| |

### Use Cases 

 * The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.
```java
 OrderLine items = new OrderLine();
 if (order.isInStock(items)) {
     total = order.getTotalPrice(items);
 }
```

 * The user loads a file of commands, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.
```java
 OrderLine items = new OrderLine();
 if (order.isInStock(items)) {
     total = order.getTotalPrice(items);
 }
```

 * The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.
```java
 OrderLine items = new OrderLine();
 if (order.isInStock(items)) {
     total = order.getTotalPrice(items);
 }
```

 * The user clicks in the display window to set a new Home location.
```java
 OrderLine items = new OrderLine();
 if (order.isInStock(items)) {
     total = order.getTotalPrice(items);
 }
```

 * The user changes the color of the environment's background.
```java
 OrderLine items = new OrderLine();
 if (order.isInStock(items)) {
     total = order.getTotalPrice(items);
 }
```

