OOLALA
====

This project implements a variety of applications that take advantage of drawing using a turtle.

Names: Nolan Burroughs, Casey Goldstein, Kyle White


### Timeline

Start Date: 
9/22/21

Finish Date: 
10/11/21

Hours Spent: 200

### Primary Roles
Nolan: Implementing the code runners and much of the javafx views

Casey: Implementing turtle graphics model and view

Kyle: Implementing the parsers and some view

### Resources Used

https://www.vecteezy.com/vector-art/630072-turtle-vector-tropical-related-line-style-icon

### Running the Program

Main class: Main in src.Oolala

Data files needed: Property files in src.oolala.model.resources,
property and css files in src.oolala.view.resources, and image files
in src.oolala.view.images

Features implemented:

All Applications: 
- A screen with turtles that follow movement commands.
- A color box to set the color of the pens
- A color box to select the color of the screen
- A display that can be shown in the selected language (English, French, Spanish)
- A text field that can load text files, edit the text, save the current
text to a file, and run the contents of the text field
- A parser that will parse the inputted text and either add the correctly
formatted code to a code runner or display the incorrect command
- A code runner that will run the code in the text field on the turtles
in the environment
- A clear button to clear the environment


Logo:
- A display containing all the currently told turtles locations and directions
- A slider to change the width of the pen of all turtles on the screen
- A text field to change the current home of the turtles
- A combobox to select the image to use for the current turtles
- A combobox to select a previously run command to run again

L-System:
- A slider that sets the move distance of the turtle
- A slider that sets the turn degree of the turtle
- A slider that sets the level to run of the code
- A text field to change the starting point of the turtle
- A combobox to select the image of the turtle
- A combobox to select a previously run command to run again

Darwin:
- Buttons to select the species currently being worked on
- The text box for coding will store and load the code for the currently
selected species
- A slider to select the radius of detection for turtles
- A text field to choose the location to add a turtle
- A button to add a turtle of the current species to the location
in the text field
- A combobox that sets the image to be used by all turtles of the currently
selected species
- A pause/play button
- When ran, the text area for each species will be parsed and each
creature will run its current code line for its current species' code

### Notes/Assumptions

Assumptions or Simplifications:

All:
All the parsers expect english commands to be input.  We could probably change
this with use of datafiles containing translation, but we ran out of time to implement
this.  Additionally, we assumed that any capitalization was valid.

JavaFX Object Styling in CSS:
Some JavaFX objects (ImageViews for example) were difficult to size using CSS. 
As a result, we opted to set some of the styling for objects (eg. TurtleView)
inside of their creation methods.

Abstractions/Polymorphism:
We spent most of our meetings together discussing to what extent we wanted to
abstract/group our classes. We knew that there were far too many similarities across
our parsers, coderunners, views, etc to keep everything independent (would lead
to a ton of duplicated code) so we opted for a hierarchy in each aspect of the
program. Initially, we planned on having three subclasses, Logo, LSystem, and Darwin,
extend an abstract class for each facet of the code. But we quickly realized that 
certain steps in our workflow were similar across the applications. For example, after
we decided to have our parser translate the LSystem code into Logo code, we could group
both LSystem and Logo under one CodeRunner and TurtleView class (after the parser, they
run code identically [i.e CodeRunner] and the display Turtle movement identically [i.e
TurtleView]). However, we decided to have all three subclasses when it came to Parsing (
all the inputted code is handled differently), the View (each Application has its own
set of buttons), etc. In this way, we believe to have developed the most intuitive hierarchy
that remained cognizant of the similarities across the applications for each given part
of the program.

Logo:
For logo we assumed that there is expected to be a turtle starting
on the screen with its pen down with the application loads.  Very few
programs we saw started with a TELL 1, so we decided to assume
there was an implied TELL 1 at the beginning of every program.

L-System:
We assumed that all L-System programs must be directly translatable
into Logo commands.  Thus, we run the code like it is Logo code and 
display the history as such.  Additionally, we assumed that every program
must have one Start command, and it must have a colon between the word
start and the starting character.  We also assumed that an L-System
is only supposed to run the selected level, not all the levels up to
the selected level.

Darwin:
We assumed that each creature in Darwin occupied a single point.
In order to decide if a creature was DIRECTIONALLY adjacent to 
another creature, we checked if the creature moving up to the given
radius would land on the other creatures point.  We also considered
doing something like a code with a given radius and degree, but
this was difficult to do with the javafx coordinate system.  We also
assumed that it was an error to run a turtle with a species with no code,
and an error to have a species with code that does not infinitely loop.

Interesting data files:
We made use of one turtle vector graphic found online (see Resources Used),
and Photoshopped to have multiple colors.
Additionally, we made use of language property files and a css sheet to house
our language and design for the program. The language property files enable us
to display messages in whatever language the user chose at the beginning of the
program.
On this note, we were deciding between creating another data file to house our
dimensions for the program (i.e display height/width,image sizes, etc) but ultimately 
decided it wouldn't save the coder or the computer any time, even as the program 
grows in complexity. There were at tops 10 numbers that we would add to the sheet, and
that quantity would be unlikely to grow as the program scales.

Known Bugs: For Logo and L-System, a turtle can run off of the
screen. Similarly, the home space inputted can be off the screen
as long as it is a number and has the correct format.  For Darwin,
using the directionality check for ifenemy/infect/etc really only
works at 90 degree angles. This is most likely due to the turtles
being a point.  We had hoped to implement a cone detection (look
for turtles in a radius withing a certain degree of the current
direction), but the code got really messy due to everything being
in a javafx coordinate system.

Extra credit: We tried to go above and beyond by using reflection
for turtle method calls and using PropertyChangeListeners in order
for the model to communicate turtle location changes to the view while
still keeping model/view separation (discussed in the Model/View separation
article).

_________________________
###Use of Program:

1. Select Language (options: English, Spanish, French)
2. Select Application (options: Logo, LSystem, Darwin)
3. "Code" Step: Import/Type Code, Add Turtles (Darwin), Adjust Parameters
4. Run!

###Pipeline of Code (walking through the classes):

####Start of Program:
Main --> Language Selector --> Application Selector --> Logo/LSystem/Darwin View and Model

Explanation: Depending on the user input, Logo/LSystem/Darwin View and Model classes are created with
default values.

####Running Code:
Parser --> CodeRunner/TurtleMethod--> TurtleModel --> TurtleView --> Repeat!

Explanation: After "Run Code" has been pressed, the respective Parser 
(Logo/LSystem,Darwin) will begin dissecting the inputted code. LSystem 
code will be converted into Logo Code. All of the parsed code will then
be turned into a TurtleMethod object, which, through reflection, will call
the respective action methods (i.e move, stamp, change direction, etc).
This reflection only changes the Model side of the turtle; that is, all of 
the data (location, direction, etc). When changes are made to these turtle 
attributes, our PropertyChangeListeners signal for the TurtleView to act 
accordingly (eg. if the turtleModel's location has been updated, then the ImageView
Object in TurtleView should be set to that new location). The CodeRunner moves
to the next line, and the next action is taken.

### Impressions
This project was interesting.  It opened up fascinating ways
to implement abstraction and lead to interesting discussions
on separating model and view when the view had to input new code.
It was a lot of work.
