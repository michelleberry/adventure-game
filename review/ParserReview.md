| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|Command parseUserCommand(String userCommand)throws InvalidCommandException | retrieve noun and verb to send to create command |none|parseFirstWord(), parseSecondWord(), createCommand()|Scanner, Command| 6 lines|
|String parseFirstWord(Scanner lineScnr)|If the first word is present in the line, return that word|none|none|Scanner|5 lines|
|String parseSecondWord(Scanner lineScnr)|If the scanner has nextline, return that phrase|none|none|Scanner|5 lines|
|Command createCommand(String firstWord, String secondWord) throws InvalidCommandException| try to create command calling appropriate constructor, throw exception thrown by command constructor |none|none|Command, InvalidCommandException| 9 lines|
|String allCommands()|Get list of valid commands, format as string to return|none|none|none|6 lines|
|String toString()|Return string representation of parser|none|none|none|1 line|