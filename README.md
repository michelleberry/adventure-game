
## Author Information

* Name: Michelle Berry
* Email: mberry05@uoguelph.ca
* Student ID: 1082031


## How to operate your program

with maven:
    $mvn compile
    $mvn exec:java

### Running from the command line (without maven)

assemble to a jar file:
    $mvn assembly:assembly

run GUI version:
    $java -jar target/2430_A2-1.0-jar-with-dependencies.jar <command line arguments>

run cmd line version:
    $java -cp target/2430_A2-1.0-jar-with-dependencies.jar adventure.Game <command line arguments>

### Instructions for using the program

Type quit to exit the program/quit the game!
You can type go <n/s/e/w/up/down> to go North , South, East, West, up, or down.
You can type look to see a description of the room you are in or type look <item> to see a description of a certain item in the same room that you are in.
You can toss, eat, read, or wear items that are tossable/edible/readable/wearable. Not every item has these characteristics. 
You can enter your own filePath as cmd line args to open a json file or serialized save, simply use the default one I have created. 

**ASSUMPTIONS THAT I MADE:
- you can look at items that are in your inventory at any time
- the setConnectedRoom() method does not automatically set a double-sided connection between the rooms.
  it simply sets a connection from this.room to the one passed as a param.  

## Statement of Individual Work

By the action of submitting this work for grading, I certify that this assignment is my own work, based on my personal study.  I also certify that no parts of this assignment have previously been submitted for assessment in any other course, except where specific permission has been granted.  Finally, I certify that I have not copied in part or whole  or otherwise plagiarised the work of other persons.

