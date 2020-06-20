package adventure;

import java.io.FileReader;
import java.io.Reader;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.InputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileOutputStream;
import java.io.FileInputStream; 
import java.io.ObjectOutputStream; 
import java.io.ObjectInputStream; 
import java.io.IOException;

public class Game implements java.io.Serializable {

    private static final long serialVersionUID = -4017025074273932581L;

    private Adventure theAdventure;
    private AdventureView gameView = null; 
    private Parser parser = new Parser();
    private Scanner scanner;  

    private String loadSaveGame; 
    private String loadNewAdventure = "michelles_adventure.json"; 

    private boolean loadSave = false; 
    private boolean loadAdventure = false; 

    /**
     * @author Michelle Berry
     * @since May 25, 2020
     * @param args
     */
    public static void main(String[] args){ 
        
        Game theGame = new Game();
        theGame.setScanner( new Scanner(System.in)); 

        theGame.parseCmdLineArgs(args);  
        if(theGame.loadStartAdventure() == null){
            System.out.println("Invalid command line arguments. Files could not be loaded.");
            System.exit(0); 
        } else{
            theGame.generateStartAdventure(theGame.loadStartAdventure());
        }
                
        theGame.beginGamePlay(theGame.getScanner(), theGame.theAdventure.getPlayer()); 
    }

    /**
     * parses command line args and changes instance variables of the game
     * accordingly
     * @param args
     */
    public void parseCmdLineArgs(String[] args){
        if(args.length > 1){
            if(args[0].equals("-a")){
                setLoadNewAdventure(args[1]);
                setLoadAdventure(true);
            } else if(args[0].equals("-l")){
                setLoadSaveGame(args[1]);
                setLoadSave(true);
            }
        }
    }

    /**
     * print beginning of adventure and begin game's game play 
     * @param scnr
     * @param player
     */
    public void beginGamePlay(Scanner scnr, Player player){
        // 4. Print the beginning of the adventure
        String intro = this.theAdventure.getCurrentRoomDescription();
        System.out.printf("\n" + intro + ">");
        boolean playing = true;
        String output = ""; 
        gamePlay(scnr, player, playing, output); 
    }

    /**
     * Runs the user through the gameplay/input loop
     * @param scnr the scanner
     * @param player the player
     * @param playing should be true
     * @param output string
     */
    public void gamePlay(Scanner scnr, Player player, boolean playing, String output){
        //start scanning + parsing player input 
        while(playing){
            try{
                String input = scnr.nextLine();
                Command c = this.parser.parseUserCommand(input);
                output = player.doCommand(c, this.theAdventure, this);
                System.out.printf(output);
            } catch (InvalidCommandException q){
                System.out.printf(q.getMessage() + "\n>");
            }
        }
    }


    /**
     * returns jsonobject loaded from file determined by cmd line arg options
     * @return loaded jsonFile
     */
    public JSONObject loadStartAdventure(){
        JSONObject jsonFile = null; 
        if(!this.loadAdventure){     //load default
            InputStream inputStream = Game.class.getClassLoader().getResourceAsStream(this.loadNewAdventure);
            jsonFile = this.loadAdventureJson(inputStream); 
        } else {                       //load new adventure file if applicable (from cmd line args)
            jsonFile = this.loadAdventureJson(this.loadNewAdventure);
        }
        return jsonFile; 
    }

    /**
     * initializes the adventure variable based on command line arg options
     * @param jsonFile
     */
    public void generateStartAdventure(JSONObject jsonFile){
        if(!getLoadSave()){
            this.theAdventure = this.generateAdventure(jsonFile); 
            this.theAdventure.getPlayer().setCurrentRoom(this.theAdventure.getCurrentRoom());
        } else {
            //load serializable
            this.theAdventure = this.deSerialize(this.loadSaveGame); 
        }
    }    

    /** opens file and parse JSON
     * @param fileName
     * @return a jsonobject
     */
    public JSONObject loadAdventureJson(String fileName){
        JSONParser jParser = new JSONParser();
        try (Reader reader = new FileReader(fileName)){
           JSONObject jsonObject = (JSONObject) jParser.parse(reader);
            
           return jsonObject;
        } catch(Exception p){
           System.out.println("File couldn't be opened.");
           System.out.println(p.getMessage());  
        }
        return null; 
    }

    /**
     * Loads adventure given the input stream.
     * @param inputStream
     * @return JSONObject 
     */
    public JSONObject loadAdventureJson(InputStream inputStream){
        try(InputStreamReader reader = new InputStreamReader(inputStream)){
            JSONParser jParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jParser.parse(reader);
            return jsonObject;
        } catch (Exception p){
            System.out.println("File couldn't be opened.");
            System.out.println(p.getMessage()); 
        }
        return null; 
    }

    /** generates adventure by splitting up JSONObject and invoking constructors
     * @return Adventure
     * @param obj a json object 
     */
    public Adventure generateAdventure(JSONObject obj){ 
       try {
           JSONObject innerObj = (JSONObject) obj.get("adventure");
           JSONArray roomList = (JSONArray) innerObj.get("room");
           JSONArray itemList = (JSONArray) innerObj.get("item");
          
           Adventure thisAdventure = new Adventure(roomList, itemList);
           return thisAdventure; 
       } catch(Exception e){
           System.out.println("Could not generate adventure:" + e.getMessage());
       }
       return null; 
    }

    /**
     * loads a previously serialized object
     * @param fileName
     * @return loaded adventure
     */
    public Adventure deSerialize(String fileName){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName)); ){ 
            
            // Method for deserialization of object 
            return (Adventure)in.readObject();               
        } catch(IOException ex){ 
            System.out.println("Could not load save file: " + ex); 
            
        } catch(ClassNotFoundException ex) { 
            System.out.println("ClassNotFoundException is caught - cannot open save file: " + ex); 
            
        } 
        return null; 
    }

    /**
     * save the Adventure passed as param using serialization.
     * @param theAdv the adventure to be saved
     * @return string confirming serialization
     * @throws IOException
     */
    public String serializeAdventure(Adventure theAdv) throws IOException {
        String saveGameName = theAdv.getPlayer().getSaveGameName();
        FileOutputStream outPutStream = new FileOutputStream(saveGameName); 
        ObjectOutputStream outPutDest = new ObjectOutputStream(outPutStream); 
                  
        // Method for serialization of object 
        outPutDest.writeObject(theAdv); 
      
        outPutDest.close(); 
        outPutStream.close(); 
         
        return ( saveGameName + " has been saved.");
    }

    /**
     * saves the current Adventure object under save name determined by user.
     * @param theAdv the current adventure
     * @param scnr the working System.in scanner
     */
    public void saveSerializedAdventure(Adventure theAdv, Scanner scnr){
        String saveGameName = saveNameQuery(scnr, "", "", false);
        theAdv.getPlayer().setSaveGameName(saveGameName);
        try{
            String toPrint = serializeAdventure(theAdv);
            System.out.println(toPrint + " Load with args <-l " + saveGameName + ">."); 
            scnr.close(); 
            System.exit(0); 
        } catch (IOException ioe){
            System.out.println(ioe); 
        }
     
    }

    /**
     * prompts user to save game, handles reponses from user
     * @param scannr
     * @param response string
     * @param toReturn string
     * @param answ should be false
     * @return  String the save name if they wish to save, else, nothing is returned 
     * and program exits.
     */
    public String saveNameQuery(Scanner scannr, String response, String toReturn, boolean answ){
        System.out.printf("\n" + this.theAdventure.getPlayer().getName() +", would you like to save your game?\n>");  
        while(!answ){
            response = scannr.nextLine();
            response = response.toLowerCase();  
            toReturn = parseResponse(response, scannr); 
            if(toReturn != null){
                answ = true; 
            }
        }
        return toReturn; 
    }

    /**
     * parse response from user
     * @param response
     * @param scannr
     * @return filename if given
     */
    public String parseResponse(String response, Scanner scannr){
        if(response.equals("yes")){
            System.out.printf("\nPlease enter a name for the saved game:\n>");
            return scannr.nextLine();
        } else if(response.equals("no")){
            scannr.close(); 
            System.exit(0);
        } else {
            System.out.printf("\nWould you like to save your game? Please only answer yes or no.\n>");
        }
        return null; 
    }

    
    /**
     * saves the current Adventure object under save name determined by user,
     * if they choose to save. 
     * @param theAdv the current adventure
     */
    public void saveAdventure(Adventure theAdv){
        if(gameView == null){
            saveSerializedAdventure(theAdv, getScanner());
        } else {
          gameView.quitSaveInquiry(); 
        }
    }

    /**
     * set parser variable
     * @param newParser
     */
    public void setParser(Parser newParser){
        parser = newParser; 
    }
    
    /**
     * set theAdventure variable
     * @param newAdv
     */
    public void setTheAdventure(Adventure newAdv){
        theAdventure = newAdv; 
    }

    /**
     * set loadSaveGame
     * @param newFilePath
     */
    public void setLoadSaveGame(String newFilePath){
        loadSaveGame = newFilePath; 
    }

    /**
     * set the loadNewAdventure variable
     * @param newFilePath
     */
    public void setLoadNewAdventure(String newFilePath){
        loadNewAdventure = newFilePath; 
    }

    /**
     * set loadSave
     * @param value
     */
    public void setLoadSave(boolean value){
        loadSave = value; 
    }

    /**
     * set loadAdventure
     * @param value
     */
    public void setLoadAdventure(boolean value){
        loadAdventure = value; 
    }

    /**
     * set scanner
     * @param newScnr a scanner
     */
    public void setScanner(Scanner newScnr){
        scanner = newScnr; 
    }

    /**
     * set new gameView
     * @param newGameView
     */
    public void setGameView(AdventureView newGameView){
        gameView = newGameView; 
    }

    /**
     * get scanner
     * @return scanner
     */
    public Scanner getScanner(){
        return scanner; 
    }

    /**
     * @return loadSave
     */
    public boolean getLoadSave(){
        return loadSave; 
    }

    /**
     * @return loadAdventure
     */
    public boolean getLoadAdventure(){
        return loadAdventure; 
    }

    /** @return theAdventure */
    public Adventure getAdventure(){
        return theAdventure; 
    }

    /** @return parser */
    public Parser getParser(){
        return parser; 
    }

    /**
     * @return String representation of object
     */
    public String toString(){
        return ( "This game loads adventure from file " + loadNewAdventure ); 
    }

}

