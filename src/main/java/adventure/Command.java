package adventure;

public class Command {
    private String action;
    private String noun;

    public static final String[] VALID_COMMANDS = {"look", "go", "take", "quit", "inventory", 
    "eat", "wear", "read", "toss"}; 
    public static final String[] VALID_SINGLE_WORD_COMMANDS = { "look", "quit", "inventory"};

    /**
     * Create a command object with default values. 
     * this is invalid so it just throws exception 
     */
    public Command() throws InvalidCommandException {
        throw new InvalidCommandException(); 
    }



    /**
     * Create a command object given only an action.  this.noun is set to null
     *
     * @param command The first word of the command. 
     * 
     */
    public Command(String command) throws InvalidCommandException{
        // validate the action word here and throw an exception if it isn't
        // a single-word action
        if(!isValidSingleWordCommand(command)){
            throw new InvalidCommandException("This command requires a second word/noun.");
        }
        this.action = command; 
        this.noun = null; 
        
    }

    /**
     * Create a command object given both an action and a noun
     *
     * @param command The first word of the command. 
     * @param what      The second word of the command.
     */
    public Command(String command, String what) throws InvalidCommandException{
        //validate the command here , throw an exception if not valid
        if(!isValidCommand(command)){
            throw new InvalidCommandException(); 
            //if noun is invalid i throw exception 
        } 
        this.action = command;
        this.noun = what;
    }

    /**
     * sets action
     * @param newAction
     */
    public void setAction(String newAction){
        action = newAction;
    }

    /**
     * sets noun
     * @param newNoun
     */
    public void setNoun(String newNoun){
        noun = newNoun; 
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     *
     * @return The command word.
     */
    public String getActionWord() {
        return this.action;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getNoun() {
        return this.noun;
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() {
        return (noun != null);
    }

    /** checks if a command noun is in the list of valid commands
     * @param command
     * @return true or false
     */
    public static Boolean isValidCommand(String command) throws InvalidCommandException{
        for( String a : VALID_COMMANDS){
            if(command.equals(a)){
                return true; 
            }
        }
        throw new InvalidCommandException(); 
        //return false; 
    }

    /** checks if a command noun is in the list of valid SIngLE WORD commands
     * @param command
     * @return true or false
     */
    public static Boolean isValidSingleWordCommand(String command) throws InvalidCommandException{
        for( String a : VALID_SINGLE_WORD_COMMANDS){
            if(command.equals(a)){
                return true; 
            }
        }
        throw new InvalidCommandException(); 
        //return false; 
    }

    /**
     * @return String representation of object
     */
    public String toString(){
        return ( action + " " + noun ); 
    }
}

