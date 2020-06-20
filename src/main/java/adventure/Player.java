package adventure;

import java.util.ArrayList;
//import java.util.Scanner; 

public class Player implements java.io.Serializable {

    private static final long serialVersionUID = 8898957652401644406L;

    private String name;
    private String saveGameName; 
    private Room currentRoom; 
    private ArrayList<Item> inventory = new ArrayList<Item>(); 

    /** a default constructor method to initialize instance vars */
    public Player(){
        setName("none");
        setSaveGameName("none");
        setCurrentRoom(null);
    }
    
    /**
     * sets the name instance variable
     * @param newName
     */
    public void setName(String newName){
        name = newName; 
    }

    /**
     * sets the current room instance variable. may be unnecessary -> also in Adventure class
     * @param room
     */
    public void setCurrentRoom(Room room){
        currentRoom = room; 
    }

    /**
     * set the save game name for the player
     * @param newSaveGameName
     */
    public void setSaveGameName(String newSaveGameName){
        saveGameName = newSaveGameName; 
    }

    /**
     * sets the inventory
     * @param newInventory
     */
    public void setInventory(ArrayList<Item> newInventory){
        inventory = newInventory; 
    }

    /**
     * return player name
     * @return name
     */
    public String getName(){
        return name; 
    }

    /**
     * return list of inventory items
     * @return arraylist of Item s
     */
    public ArrayList<Item> getInventory(){
        return inventory; 
    }

    /**
     * return the current room
     * @return currentRoom
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }

    /**
     * return the save game name
     * @return saveGameName
     */
    public String getSaveGameName(){
        return saveGameName; 
    }

    /**
     * adds item given as parameter to player inventory
     * @param newItem
     */
    public void addToInventory(Item newItem){
        inventory.add(newItem);
    }

    /**
     * removes item from player's inventory
     * @param toRemove
     */
    public void removeFromInventory(Item toRemove){
        inventory.remove(toRemove);
    }

    /**
     * Finds command to execute and triggers corresponding method
     * @param c the command
     * @param adv the current adventure
     * @param game the current game (used to call methods from Game class)
     * @return the output string
     */
    public String doCommand(Command c, Adventure adv, Game game){
        String output = ""; 
        if(c.getActionWord().equals("inventory")){
            output = adv.showInventory(); 
        } else if(c.getActionWord().equals("quit")){
            game.saveAdventure(adv); 
        } else {  
            output = doCommandTwo(c, adv, game);
        }
        return output; 
    }

    /**
     * checks if command is look, take, or go and 
     * triggers corresponding method
     * @param c
     * @param adv
     * @param game
     * @return output string for user to see
     */
    public String doCommandTwo(Command c, Adventure adv, Game game){ 
        if(c.getActionWord().equals("look")){
            //execute look
            return adv.executeLook(c.getNoun());
        } else if(c.getActionWord().equals("take")){
            //execute take
            return adv.executeTake(c.getNoun()); 
        } else if(c.getActionWord().equals("go")){
            //execute go
            return adv.executeGo(c.getNoun()); 
        } else {
            return doCommandThree(c, adv, game);
        }
    }

    /**
     * check if command is eat, toss, or wear (new commands from A3).
     * @param c command
     * @param adv adventure
     * @param game game
     * @return output string
     */
    public String doCommandThree(Command c, Adventure adv, Game game){ 
        if(c.getActionWord().equals("eat")){
            return adv.eatFood(c.getNoun()); 
        } else if(c.getActionWord().equals("toss")){
            return adv.tossItem(c.getNoun());
        } else if(c.getActionWord().equals("read")){
            return adv.readItem(c.getNoun());
        } else if(c.getActionWord().equals("wear")){
            return adv.wearClothing(c.getNoun()); 
        }
        return ""; 
    }

    /**
     * @return String representation of object
     */
    public String toString(){
        return ( name + "saved as: " + saveGameName + ". Saved in current room " + currentRoom.toString() ); 
    }
    
}
