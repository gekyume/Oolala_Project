## Lab Discussion
### Team 19
### Nolan Burroughs, Casey Goldstein, Kyle White


### Issues in Current Code

Abstraction on Model and View - need to make 'top' classes with universal methods, then have a three subclasses (for both Model and View) for Logo, L-System, and Darwin.

#### View Class
* Design issue:
Need 'set up' screen where we receive speaking and coding language/application
* Design issue:
Need to create abstract View class that has universal methods/visual elements for all of coding applications, then make subclasses for Logo, L-System, and Darwin.
* Design issue:
Need to move magic numbers to css files
* Design issue:
One of the classes (makeTurtleScreen) is longer than it needs to be.

#### Model Class
* Design issue:
Current model is set up for logo only
* Design issue:


### Refactoring Plan

* What are the code's biggest issues?
The main view and model classes are only set up for logo.  Addtionally,
we have a large amount of magic numbers.

* Which issues are easy to fix and which are hard?
Getting rid of the magic numbers should be relatively simple to do with css.
Abstracting the main view and model will be slightly more difficult,
but helpful in the long run.

* What are good ways to implement the changes "in place"?
Add a lot of the view information to css files.

### Refactoring Work

* Issue chosen: Fix and Alternatives
Git rid of some magic numbers

* Issue chosen: Fix and Alternatives