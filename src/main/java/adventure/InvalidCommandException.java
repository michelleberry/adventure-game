package adventure;

public class InvalidCommandException extends Exception {
    private static final long serialVersionUID = 7344818809103662344L;

    public InvalidCommandException() {
        super("\nInvalid Command. Valid commands: go <N/S/E/W/up/down>, look <item>, take <item>, inventory,"
         + "eat <item>, wear <item>, toss <item>, quit.");
    }

    public InvalidCommandException(String message){
        super(message); 
    }

    /**
     * @return String representation of object
     */
    public String toString(){
        return ( "This class throws exceptions when invalid commands are entered." ); 
    }
}
