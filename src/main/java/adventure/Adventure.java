package adventure;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Adventure implements java.io.Serializable{

    private static final long serialVersionUID = -432061972944196819L;

    /* some private member variables */
    private ArrayList<Room> rooms = new ArrayList<Room>(); 
    private ArrayList<Item> items = new ArrayList<Item>(); 

    private Room currentRoom; 
    private Player player = new Player(); 

    /* ======== Required public methods ========== */
        /* note,  you don't have to USE all of these
        methods but you do have to provide them.
        We will be using them to test your code */

    /**
     * a default constructor for Adventure
     */
    public Adventure() {
       setCurrentRoom(null); 
    }

    /** a constructor for Adventure 
     * @param roomsJsonArray
     * @param itemsJsonArray
    */
    public Adventure( JSONArray roomsJsonArray, JSONArray itemsJsonArray ) throws Exception {
        this.setAllItems(itemsJsonArray);
        this.setAllRooms(roomsJsonArray);

        //then pass each room through another loop to set room connections (which are instance vars for Room)...
        for(int q = 0; q < roomsJsonArray.size(); q++){  
            rooms.get(q).setConnectedRooms(rooms);
        }

        //finally, initialize what room each item is in in instance vars for each item...
        for(int p = 0; p < itemsJsonArray.size(); p++){
            items.get(p).setRoomContainingItem(rooms); 
        }

        this.setStart(); 
        roomJsonTesting();
    }

    /**
     * calls methods which test the json was formatted properly
     * @throws Exception
     */
    public void roomJsonTesting() throws Exception {
        testValidRoomConnections(); 
        testInvalidEntranceRoomIds();
        testInvalidLootItemIds();
    }

    /**
     * return a list of ids derived from all rooms
     * @return list of ids
     */
    public ArrayList<Long> getValidRoomIds(){
        ArrayList<Long> validRoomIds = new ArrayList<Long>(); 
        for(Room room : rooms){
            validRoomIds.add(room.getId());
        }
        return validRoomIds; 
    }

    /**
     * make sure all entrance from json file has valid room id
     * @throws Exception
     */
    public void testInvalidEntranceRoomIds() throws Exception {
        //collect all valid room ids
        ArrayList<Long> validRoomIds = getValidRoomIds(); 
        //check that each room's entrance ids can be found in the set of 
        //valid room ids
        for(Room roomba : rooms){
            ArrayList<Long> exitIds = roomba.getEntranceIds();
            for( Long eId : exitIds){
                if(!(validRoomIds.contains(eId))){
                    throw new Exception("Room has invalid entrance Id values"); 
                }
            }
        }
    }

    /**
     * return a list of ids derived from all items
     * @return list of ids
     */
    public ArrayList<Long> getValidItemIds(){
        ArrayList<Long> validItemIds = new ArrayList<Long>();
        for(Item item : items){
            validItemIds.add(item.getId());
        }
        return validItemIds; 
    }

    /**
     * make sure all loot in json file has valid id
     */
    public void testInvalidLootItemIds() throws Exception {
        //collect all valid item ids
        ArrayList<Long> validItemIds = getValidItemIds(); 
        //check that each loot id is a valid item id
        for(Room room : rooms){
            ArrayList<Long> lootIds = room.getLootIds();
            for(Long lid : lootIds){
                if(!validItemIds.contains(lid)){
                    throw new Exception("A Room in your JSON has invalid loot id (not an item)");
                }
            }
        }
    }

    /**
     * ensure each room has at least one connection
     * and that connections are valid (properly double - sided)
     * @throws Exception
     */
    public void testValidRoomConnections() throws Exception {
        ensureAllRoomsHaveExits();
        ensureAllRoomExitsCorrelate(); //nsew matches up
    }

    /**
     * cycle through all rooms checking with ensureRoomHasExits()
     * @throws Exception
     */
    public void ensureAllRoomsHaveExits() throws Exception {
        for( Room room : rooms){
            ensureRoomHasExits(room);
        }
    }

    /**
     * make sure a room has at least one exit
     * @param toTest
     * @throws Exception
     */
    public void ensureRoomHasExits(Room toTest) throws Exception {
        String[] dirsToTest = {"N","S","E","W","up","down"};
        int exits = 0; 
        for( String dir : dirsToTest){
            if(toTest.getConnectedRoom(dir) != null){
                exits++; 
            }
        }
        if(exits == 0){
            throw new Exception("There is a room with no exits");
        }
    }

    /**
     * cycles through all rooms checking them with ensureRoomExitsCorrelate() 
     * @throws Exception
     */
    public void ensureAllRoomExitsCorrelate() throws Exception{
        String[] goDirs = {"N","S","E","W","up","down"};
        String[] leaveDirs = {"S","N","W","E","down","up"};
        for(Room room : rooms){
            ensureRoomExitsCorrelate(room, goDirs, leaveDirs);
        }
    }

    /**
     * ensures that a room have correct double sided connection
     * @param room
     * @param goDirs
     * @param leaveDirs
     * @throws Exception if room connection is invalid
     */
    public void ensureRoomExitsCorrelate(Room room, String[] goDirs, String[] leaveDirs) throws Exception{
        for(int i = 0; i < goDirs.length ; i++){
            if(room.getConnectedRoom(goDirs[i]) != null){ //if there IS a connection, check if it is not valid
                if(room.getConnectedRoom(goDirs[i]).getConnectedRoom(leaveDirs[i]) != room){
                    throw new Exception("Invalid Room Connections"); //if not valid throw exception
                }
            }        
        }
    }

    /**
     * Initializes rooms arraylist.
     * @param roomsJsonArray
     */
    public void setAllRooms(JSONArray roomsJsonArray){
        for (int i = 0; i < roomsJsonArray.size(); i++){
            JSONObject tempRoom = (JSONObject) roomsJsonArray.get(i); 
            Room thisRoom = new Room(tempRoom,items); 
            rooms.add(thisRoom);  
        }
    }

    /**
     * Initializes items arraylist.
     * @param itemsJsonArray
     */
    public void setAllItems(JSONArray itemsJsonArray){
        for(int x = 0; x < itemsJsonArray.size(); x++){
            JSONObject tempItem = (JSONObject) itemsJsonArray.get(x); 
            
            Item thisItem = setItemType(tempItem); 
            items.add(thisItem);
        }
    }

    
    /**
     * check if item is edible or wearable, else trigger
     * method that checks remaining types
     * @param someItem
     * @return item that is instance of correct type
     */
    public Item setItemType(JSONObject someItem){
        //check type of ITEM!! here
        Item toReturn = null; 
        if(someItem.containsKey("edible") && (boolean)someItem.get("edible").equals(true)){
            //make an edible item...
            toReturn = setFoodItemType(someItem);
        } else if(someItem.containsKey("wearable") && (boolean)someItem.get("wearable").equals(true)){
            toReturn = setClothingItemType(someItem);  
        } else {
            toReturn = setItemTypeTwo(someItem);
        }
        return toReturn; 
    }

    /**
     * check if item is weapon or spell 
     * @param someItem
     * @return item that is instance of correct type
     */
    public Item setItemTypeTwo(JSONObject someItem){
        Item toReturn = null; 
        if(someItem.containsKey("tossable") && (boolean)someItem.get("tossable").equals(true)){
            //make an edible item...
            toReturn = new Weapon(someItem); 
        } else if(someItem.containsKey("readable") && (boolean)someItem.get("readable").equals(true)){
            toReturn = new Spell(someItem);  
        } else {
            toReturn = new Item(someItem);
        }
        return toReturn; 
    }

    /**
     * if item is edible, check if SmallFood or Food
     * @param someItem
     * @return item that is instance of correct type
     */
    public Item setFoodItemType(JSONObject someItem){
        if(someItem.containsKey("tossable") && (boolean)someItem.get("tossable").equals(true)){
            return new SmallFood(someItem); 
        }
        return new Food(someItem); 
    }

    /**
     * if item is wearable, check if BrandedClothing or Clothing
     * @param someItem
     * @return item that is instance of correct type
     */
    public Item setClothingItemType(JSONObject someItem){
        if(someItem.containsKey("readable") && (boolean)someItem.get("readable").equals(true)){
            return new BrandedClothing(someItem); 
        }
        return new Clothing(someItem); 
    }
    
    /** sets the room with start = true as the current room  */
    public void setStart(){
        for (int i=0; i < rooms.size(); i++){
            Room tempRoom = rooms.get(i); 
            if(tempRoom.getStart().equals("true")){   //look for the room with the start:true tag 
                setCurrentRoom(tempRoom); 
            }
        } 
    }

    /** sets the current room as the room passed in as parameter
     *  @param room 
     */
    public void setCurrentRoom(Room room){
        //do i need this? or can i set it automatically in other methods, ill see
        currentRoom = room; 
    }

    /** set the player for the adventure
     * @param newPlayer
     */
    public void setPlayer(Player newPlayer){
        player = newPlayer; 
    }

    /**
     * sets rooms
     * @param newRooms
     */
    public void setRooms(ArrayList<Room> newRooms){
        rooms = newRooms; 
    }
    
    /**
     * sets items
     * @param newItems
     */
    public void setItems(ArrayList<Item> newItems){
        items = newItems; 
    }

    /** @return a list of all rooms in game */
    public ArrayList<Room> listAllRooms(){
        return rooms; 
    }

    /** @return a list of all items in game */
    public ArrayList<Item> listAllItems(){
        return items; 
    }

    /** returns descriptions of the currentRoom
     * @return the descriptions 
     */
    public String getCurrentRoomDescription(){
        return (currentRoom.getShortDescription() + ".\n" + currentRoom.getLongInfo() ); 
    }

    /** returns the current room the player is in
     * @return current room
     */
    public Room getCurrentRoom(){
        return currentRoom; 
    }

    /**
     * returns the player
     * @return the player
     */
    public Player getPlayer(){
        return player; 
    }

    /** executes the "go" command. go N,S,W, or E.
     * @param direction
     * @throws Exception
     * @return the output string
     */
    public String executeGo(String direction) {
        String output = ""; 
        //check if you can move there from current room
        direction = direction.toUpperCase(); 
        Room connRoom = currentRoom.getConnectedRoom(direction);
        if(connRoom != null){
            //update current room
            setCurrentRoom(connRoom); 
            //print new description
            output = ("\n" + connRoom.getLongInfo() ); 
        } else {
            output = ("\nYou can't go there.\n"); 
        }
        return output;
    }

    /**executes the look command
     * @param theItem what you wanna look at 
     * @return the output string
     */
    public String executeLook(String theItem){
        String output = ""; 
        if(theItem == null || theItem.isBlank()){
            output = ("\n" + currentRoom.getLongInfo() ); 
        } else {
            output = findItemToLookAt(theItem);
        }
        return output; 
    }

    /**
     * see if item is available for looking or not and return appropriate response for user.
     * @param theItem
     * @return output (What will be printed out to the user)
     */
    public String findItemToLookAt(String theItem){  
        if(isAnItem(theItem) && (this.itemInRoom(theItem) || this.itemInInventory(theItem)) ){
            //check if the item is in current room or inventory 
            return ("\n" + getItemFromName(theItem).getDescription()+ "\n"); 
        }
        //if nothing has been returned, the item cannot be looked at so we return a error message
        return ("\nI don't know what you mean by " + theItem + ". Try a different phrase.\n");
    }

    /**
     * takes an item from the room and moves it to the player inventory
     * @param theItem
     * @return the output string to be printed by main
     */
    public String executeTake(String theItem){
        String output = ""; 
        if(theItem == null){
            output = ("You must tell me what item you want to take.\n"); 
        } else {
            output = takeItem(theItem);
        }
        return output; 
    }

    /**
     * see if the named item exists and is good for taking. if it is, return the item
     * @param theItem name as a string
     * @return the item
     */
    public Item findItemToTake(String theItem){
        //check if the item is in current room and is actually an valid item
        if(isAnItem(theItem) && this.itemInRoom(theItem)){
            return getItemFromName(theItem); //if you find it return it
        }
        //if nothing has returned, the item cannot be taken.
        return null; 
    }

    /**
     * if item can be taken, take it
     * @param theItemName
     * @return string describing what happened
     */
    public String takeItem(String theItemName){
        if(findItemToTake(theItemName) != null){ //if you can find it
            Item theItem = findItemToTake(theItemName); 
            this.player.addToInventory(theItem); //add to player inventory
            currentRoom.removeItemFromRoom(theItem);          //remove from room 
            return ("\n" +theItem.getName() +" was put into your inventory.\n"); 
        } else {
            return ("\nI don't know what you mean by " + theItemName + ". Try a different phrase.\n");
        }
    }

    /**
     * returns true if item is valid item in the adventure
     * @param itemName
     * @return true or false
     */
    public boolean isAnItem(String itemName){
        for(Item item : items){
            if(item.getName().equals(itemName)){
                return true; 
            }
        }
        return false; 
    }

    /**
     * returns item if item is a valid item in the adventure
     * @param itemName
     * @return item
     */
    public Item getItemFromName(String itemName){
        for(Item item : items){
            if(item.getName().equals(itemName)){
                return item; 
            }
        }
        return null; 
    }

    /**
     * checks if an item is in the player's inventory
     * @param itemName the name of the item
     * @return true or false if not in inventory
     */
    public boolean itemInInventory(String itemName){
        ArrayList<Item> inventory = this.player.getInventory();
        for(Item i: inventory){
            if(i.getName().equals(itemName)){
                return true; 
            }
        }
        return false; 
    }

     /**
     * checks if an item is within the current room the player is in
     * @param itemName the name of the item to check
     * @return true or false
     */
    public boolean itemInRoom(String itemName){
        for(Item i : currentRoom.listItems()){
            if(i.getName().equals(itemName)){
                return true; 
            }
        }
        return false; 
    }

    /**
     * returns a string formatted list of the inventory's contents
     * @return the list of inventory items as a string
     */
    public String showInventory(){
        //String output = "\nInventory contents:\n";
        String output = "\n"; 
        ArrayList<Item> inventory = this.player.getInventory(); 
        for(Item i : inventory){
            output = output + itemDisplay(i);
        }
        if (inventory.size() < 1){
            output = "\nYour inventory is empty.\n";
        }
        return output; 
    }

    /**
     * return item name, or item name and if wearing if clothing item
     * @param i item
     * @return item name
     */
    public String itemDisplay(Item i){
        if(i instanceof Clothing){
            Clothing item = (Clothing)i; 
            return item.getName() + " - wearing: " + item.getWearing() + "\n"; 
        } 
        return i.getName() + "\n";
    }

    /**
     * check if item is valid and can be eaten
     * @param itemName
     * @return the Food item if valid, or null
     */
    public Food findItemToEat(String itemName){
        //is it close enough to touch/eat?
        boolean available = (this.itemInInventory(itemName));
        //is it a valid Item in the adventure?
        if(isAnItem(itemName) && available){
            //is it edible?
            Item item = getItemFromName(itemName);
            if(item instanceof Food){
                return (Food)item; //if yes to all return the item
            } else if(item instanceof SmallFood){
                return (SmallFood)item; 
            }
        }
        //if nothing has returned by this point, the item cannot be taken.
        return null; 
    }

    /**
     * if food is valid to eat, remove from adventure
     * @param toEat name of food to eat 
     * @return string confirming food was eaten, or was not eaten
     */
    public String eatFood(String toEat){
        Food food = findItemToEat(toEat); 
        if(food != null ){                //can i eat it?
            //remove from inventory
            this.player.removeFromInventory(food); 
            return food.eat(); //tell them it's been eaten
        }
        return ("\nI'm not sure that you should eat that! You might need to take it first.");  
    }

    /**
     * see if item can be tossed, and if so return that item
     * @param toToss name of item 
     * @return item 
     */
    public Item findItemToToss(String toToss){
        if(isAnItem(toToss) && itemInInventory(toToss)){
            //is it edible?
            Item item = getItemFromName(toToss);
            if(item instanceof Weapon){
                return (Weapon)item; //if yes to all return the item
            } else if(item instanceof SmallFood){
                return (SmallFood)item; 
            }
        }
        //if nothing has returned by this point, the item cannot be taken.
        return null;
    }

    /**
     * toss item if it can be tossed
     * @param toToss
     * @return output string
     */
    public String tossItem(String toToss){
        Item tossMe = findItemToToss(toToss);
        if(tossMe != null){
            this.player.removeFromInventory(tossMe);
            this.currentRoom.addItemToRoom(tossMe); 
            if(tossMe instanceof Weapon){
                return ((Weapon)tossMe).toss();
            } // else must be SmallFood (else statement cut to save lines lol) 
            return ((SmallFood)tossMe).toss(); 
        } //else you cant toss it
        return "You can't toss that, sorry. Try taking it first.\n"; 
    }

    /**
     * see if item can be read and return the item
     * @param toRead name of item
     * @return item to be read
     */
    public Item findItemToRead(String toRead){
        if(isAnItem(toRead) && (this.itemInInventory(toRead) || this.itemInRoom(toRead))){
            //is it edible?
            Item item = getItemFromName(toRead);
            if(item instanceof Spell){
                return (Spell)item; //if yes to all return the clothing item
            } else if(item instanceof BrandedClothing){
                return (BrandedClothing)item; 
            }
        }
       //if nothing has returned by this point, the item cannot be worn.
       return null;
    }

    /**
     * read item if it can be read
     * @param toRead name of item
     * @return output string
     */
    public String readItem(String toRead){
        //findItemToRead; 
        Item readMe = findItemToRead(toRead);
        if(readMe != null){
            if(readMe instanceof Spell){
                return ((Spell)readMe).read(); //if yes to all return the clothing item
            } else if(readMe instanceof BrandedClothing){
                return ((BrandedClothing)readMe).read(); 
            }
        }
        //if you can den return the string 
        return "You can't read that, sorry\n"; 
    }

    /**
     * check if item is clothing and in inventory
     * @param toWear name of desired item
     * @return item if valid to wear
     */
    public Clothing findClothingToWear(String toWear){
        if(isAnItem(toWear) && this.itemInInventory(toWear)){
            //is it wearable?
            Item item = getItemFromName(toWear);
            if(item instanceof Clothing){
                return (Clothing)item; //if yes to all return the clothing item
            } else if(item instanceof BrandedClothing){
                return (BrandedClothing)item; 
            }
        }
       //if nothing has returned by this point, the item cannot be worn.
       return null; 
    }

    /**
     * wear clothing if it can be worn
     * @param toWear item name
     * @return output string
     */
    public String wearClothing(String toWear){
        Clothing wearMe = findClothingToWear(toWear);
        if(wearMe != null){
            return wearMe.wear(); 
        }
        //if you can then wear it (indicate somehow in inventory)
        return "You can't wear that, you might have to pick it up first";
    }

    /**
     * @return String representation of object
     */
    public String toString(){
        return ( "Save game:" + player.getSaveGameName() + "Player is in " + currentRoom.getName()); 
    }
}



