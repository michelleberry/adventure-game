package adventure;
import java.util.Scanner; 

public class Parser {

    /**
     * parses user input
     * 
     * @param userCommand the input string
     * @return new Command
     * @throws InvalidCommandException
     */
    public Command parseUserCommand(String userCommand) throws InvalidCommandException{
        Scanner lineScnr = new Scanner(userCommand); 
        String firstWord = parseFirstWord(lineScnr);
        String secondWord = parseSecondWord(lineScnr); 
        lineScnr.close();

        Command command = createCommand(firstWord, secondWord); //throws exception if command invalid or empty
        
        return command; //else returns valid command.
    }

    /**
     * If the first word is present in the line, return that word
     * @param lineScnr
     * @return firstWord
     */
    public String parseFirstWord(Scanner lineScnr){
        String firstWord = null; 
        if(lineScnr.hasNext()){
            firstWord = lineScnr.next(); 
        }
        return firstWord; 
    }

    /**
     * If there is more text present after the first word, return the second word/phrase
     * (I say phrase because items can be two words).
     * @param lineScnr
     * @return secondWord
     */
    public String parseSecondWord(Scanner lineScnr){
        String secondWord = null; 
        if(lineScnr.hasNextLine()){
            secondWord = lineScnr.nextLine();
            secondWord = secondWord.trim(); 
        }
        return secondWord; 
    }

    /**
     * creates a new command and returns it
     * @param firstWord
     * @param secondWord
     * @return hh
     * @throws InvalidCommandException
     */
    public Command createCommand(String firstWord, String secondWord) throws InvalidCommandException {
        Command command = null; 
        if(firstWord == null) {
            throw new InvalidCommandException();
        } else if(secondWord == null) {
            command = new Command(firstWord);
        } else {
            command = new Command(firstWord, secondWord); //throws exception to gamePlay method if INVALID
        }
        return command; 
    }
    
    /**
     * return a list of all possible commads in a printable form
     * ask command class for the list
     * @return list of all valid commands
     */
    public String allCommands(){
        String[] validCommands = Command.VALID_COMMANDS;
        String toReturn = "Valid commands are: "; 
        for(String valid : validCommands){
            toReturn = toReturn + valid + "\n"; 
        } 
        return toReturn; 
    }

    /**
     * @return String representation of object
     */
    public String toString(){
        return ( "This class parses commands created with command class.\n" + allCommands() ); 
    }
}
