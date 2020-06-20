package adventure;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;

public class Room implements java.io.Serializable {
 
    private static final long serialVersionUID = -6138676869001270923L;
    /* you will need to add some private member variables */
    private long id; 
    private String start; 
    private String name; 
    private String shortDescription; 
    private String longDescription; 

    //stores info about rooms you can enter from this room used to set connected rooms hashmap (see below). 
    private ArrayList<Long> entranceIds = new ArrayList<Long>();      
    private ArrayList<String> entranceDirs = new ArrayList<String>();
    private ArrayList<Long> lootIds = new ArrayList<Long>(); 
    //stores items that the room contains
    private ArrayList<Item> items = new ArrayList<Item>();     
   
    //stores references to connected rooms in each direction. if there is none the reference will be null.
    private HashMap<String, Room> connectedRooms = new HashMap<String, Room>(); 

    /** a default constructor method for Room */
    public Room(){
        this.setId(0); 
        this.setName("none"); 
        this.setShortDescription("none");
        this.setLongDescription("none");
        this.setConnectedRooms();  
    }

    /**a constructor method which accepts a json room object
     * @param roomObject
     */
    public Room(JSONObject roomObject){
        this.setId((long) roomObject.get("id"));  
        this.setName((String) roomObject.get("name"));
        this.setShortDescription((String) roomObject.get("short_description")); 
        this.setLongDescription((String) roomObject.get("long_description")); 
        //set start tag to true if present in object
        setStartIfPresent(roomObject);
        //initalize connected rooms to defaults (for now) to avoid notfound errors
        this.setConnectedRooms(); 
    }

    /** a constructor method for Room 
     * @param roomObject
     * @param allItems
     */
    public Room(JSONObject roomObject, ArrayList<Item> allItems){
        this(roomObject);
    
        //fill items with items in room if loot key present 
        if(roomObject.containsKey("loot")){
           JSONArray lootList = (JSONArray) roomObject.get("loot"); 
           placeLootInRoom(lootList, allItems);
        }

        //extract info about entrances for later
        if(roomObject.containsKey("entrance")){
            JSONArray entranceList = (JSONArray) roomObject.get("entrance");
            this.storeEntranceIdsDirs(entranceList);
        } 

    }

    /**
     * stores entrance ids and directions in array list for later use to fill in 
     * connected rooms hashmap.
     * @param entranceList
     */
    public void storeEntranceIdsDirs(JSONArray entranceList){
        for(int e = 0; e < entranceList.size(); e++){
            JSONObject currentEnt = (JSONObject) entranceList.get(e);
            entranceIds.add( (Long) currentEnt.get("id") ); 
            entranceDirs.add( (String) currentEnt.get("dir") ); 
        }
    }

    /** finds a room given an id number
     * @return room 
     * @param allRooms
     * @param thisId
     */
    public Room findRoomFromId(long thisId, ArrayList<Room> allRooms){
        Room theRoominQuestion = null; 
        for(int i = 0; i < allRooms.size(); i++){
            if((allRooms.get(i)).getId() == thisId){
                theRoominQuestion = allRooms.get(i); 
            }
        }
        return theRoominQuestion; 
    }

    /**places loot mentioned in lootList into the room
     * @param lootList contains id of loot in this.Room
     * @param allItems we search for the relevant items that should be here
     */
    public void placeLootInRoom(JSONArray lootList, ArrayList<Item> allItems){
        for(int p = 0; p < lootList.size(); p++){
            JSONObject tempItem = (JSONObject) lootList.get(p);
            long tempId = (long) tempItem.get("id");   //get the id of each item in list
            lootIds.add(tempId); //save each loot id for future reference
            findLootToPlace(allItems, tempId);
        }
    }

    /**
     * if the param id matches that of an item, add it to the list of
     * items in the room
     * @param allItems
     * @param searchId
     */
    public void findLootToPlace(ArrayList<Item> allItems, long searchId){
        for(Item anItem : allItems){ //find corresponding item and add to list of items in room 
            if(searchId == (anItem.getId())){
                items.add(anItem); 
            }
        }
    }

    /**
     * checks if the start tag is present in the room json object,
     * if it is not present start must be false. 
     * @param roomObject
     */
    public void setStartIfPresent(JSONObject roomObject){
        if(roomObject.containsKey("start")){
            setStart((String) roomObject.get("start"));
        } else {
            setStart("false"); 
        }
    }

    /**
     * sets room id as parameter
     * @param newId
     */
    public void setId(long newId){
        id = newId;
    }

    /**
     * sets start as parameter
     * @param toSet
     */
    public void setStart(String toSet){
        start = toSet; 
    }

    /**
     * sets room name as parameter
     * @param newName
     */
    public void setName(String newName){
        name = newName; 
    }

    /**
     * sets room's short description
     * @param newShDesc
     */
    public void setShortDescription(String newShDesc){
        shortDescription = newShDesc; 
    }

    /**
     * sets room's long description
     * @param newLongDesc
     */
    public void setLongDescription(String newLongDesc){
        longDescription = newLongDesc; 
    }

    /** this is an additional method used in the Adventure constructor that initializes variables for adjacent rooms.
     *  it loops to initialize all rooms from the list of ID's and Directions at once. 
     * @param allRooms a list of all the rooms used to search for ids of connected ones
    */
    public void setConnectedRooms(ArrayList<Room> allRooms){
        String dir; 
        for(int i = 0; i < entranceDirs.size(); i++){
            dir = entranceDirs.get(i); 
            Room toConnect = findRoomFromId(entranceIds.get(i), allRooms);
            this.setConnectedRoom(dir, toConnect);    
        }

    }

    /**
     * Connects the given room to this.Room in the given direction
     * @param dir the direction as a string (Valid Directions: n ,s, e, w, up, down  -- case insensitive)
     * @param toConnect the room which you want to connect
     */
    public void setConnectedRoom(String dir, Room toConnect){
        dir = dir.toUpperCase(); //since nsew in upper case this will make my life easier!
        String[] validDirs = {"N", "S", "E", "W", "UP", "DOWN"};
        for(String s : validDirs){
            if(s.equals(dir)){
                //set
                connectedRooms.put(dir,toConnect);
            }
        }

    }

    /** sets room connections to default if they do not exist (sets keys we need) */
    public void setConnectedRooms(){
        connectedRooms.putIfAbsent("N", null); 
        connectedRooms.putIfAbsent("S", null); 
        connectedRooms.putIfAbsent("E", null); 
        connectedRooms.putIfAbsent("w", null);
        connectedRooms.putIfAbsent("UP", null);
        connectedRooms.putIfAbsent("DOWN", null);
    }

    /**
     * set Items
     * @param newItems
     */
    public void setItems(ArrayList<Item> newItems){
        items = newItems; 
    }
    
    /**
     * set entrance ids
     * @param newIds
     */
    public void setEntranceIds(ArrayList<Long> newIds){
        entranceIds = newIds;
    }
    
    /**
     * set entrance dirs
     * @param newDirs
     */
    public void setEntranceDirs(ArrayList<String> newDirs){
        entranceDirs = newDirs; 
    }

    /**
     * set loot ids
     * @param newLootIds
     */
    public void setLootIds(ArrayList<Long> newLootIds){
        lootIds = newLootIds; 
    }

    /**
     * removes item from room's list of item
     * @param toRemove
     */
    public void removeItemFromRoom(Item toRemove){
        items.remove(toRemove);
    }

    /**
     * add item to room's items
     * @param toAdd
     */
    public void addItemToRoom(Item toAdd){
        items.add(toAdd);
    }

    /** @return the id number */
    public long getId(){
        return id; 
    }

    /** @return the value of start - will be null unless it is the starting room */
    public String getStart(){
        return start; 
    }

    /** @return a list of all the items in the room */
    public ArrayList<Item> listItems(){
        return items; 
    }

    /**
     * returns string formatted list of items in current room
     * @return list of items as string
     */
    public String listOfItems(){
        if(items.size() > 0){
            String output = "\nItems in this room:\n"; 
            for(Item i : items){
                output = output + i.getName() + "\n";
            }
            return output;
        }
        return "\n"; 
    }

    /** @return the name */
    public String getName(){
        return name; 
    }

    /** @return the short description */
    public String getShortDescription(){
        return shortDescription; 
    }

    /** @return the long description */
    public String getLongDescription(){
        return longDescription; 
    }
    
    /** @return the description and list of items*/
    public String getLongInfo(){
        return longDescription + this.listOfItems();
    }

    /** returns connected room in given direction
     *  @return Room - null if invalid (key/direction not exist) should return null
     *  @param direction
     */
    public Room getConnectedRoom(String direction) {
        direction = direction.toUpperCase(); 
        if(connectedRooms.containsKey(direction)){
            return connectedRooms.get(direction);
        }
        return null;  //if room not found
    }

    /**
     * @return entrance ids
     */
    public ArrayList<Long> getEntranceIds(){
        return entranceIds;
    }
    
    /**
     * @return entrance directions
     */
    public ArrayList<String> getEntranceDirs(){
        return entranceDirs; 
    }

    /**
     * @return loot id numbers
     */
    public ArrayList<Long> getLootIds(){
        return lootIds;
    }

    /**
     * @return String representation of object
     */
    public String toString(){
        return ( "Room name: " + name +"\nRoom id : " + id + "\nRoom description: " + longDescription ); 
    }

    

    


}


