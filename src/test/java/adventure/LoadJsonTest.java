package adventure;

import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class LoadJsonTest {

    private Adventure adv;

    @Before
    public void setup() {
        adv = new Adventure();

    }

    @Test(expected = Exception.class)
    public void testValidRoomConnections() throws Exception{
        System.out.println("Testing rooms with incorrect connections throws error");

        Room roomA = new Room(); //initialize two new rooms
        Room roomB = new Room();

        //set invalid (not correlating) room connections -- correlating would be n/s , e/w, up/down
        roomA.setConnectedRoom("N", roomB);
        roomB.setConnectedRoom("E", roomA);

        ArrayList<Room> setOfRooms = new ArrayList<Room>();
        setOfRooms.add(roomA);
        setOfRooms.add(roomB);
        adv.setRooms(setOfRooms); //add the two rooms to the adventure

        String errorMsg = ""; 
        try {
            adv.testValidRoomConnections(); //should throw exception
        } catch (Exception e) {
           errorMsg = e.getMessage(); //store exception message
           throw e; //we catch and throw again
        } 
        assertTrue(errorMsg.equals("Invalid Room Connections")); //ensure correct exception was thrown
    }

    @Test(expected = Exception.class)
    public void testRoomHasExits() throws Exception {
        System.out.println("Testing room without any exits/entrances throws exception");

        Room roomA = new Room(); //initialize a room without connections

        ArrayList<Room> setOfRooms = new ArrayList<Room>();
        setOfRooms.add(roomA);
        adv.setRooms(setOfRooms); //add it to the adventure
    
        adv.ensureAllRoomsHaveExits(); //test should throw exceptions
    }

    @Test(expected = Exception.class)
    public void testValidRoomIds() throws Exception{
        System.out.println("Testing that a room having entrance ids which dont belong to a real room throws an error");
        Room roomA = new Room(); //create rooms
        Room roomB = new Room();
        roomA.setId((long)100);  //with ids
        roomB.setId((long)101); 

        ArrayList<Long> enterIds = new ArrayList<Long>(); //create first room's entrance ids
        enterIds.add((long)101); //valid
        enterIds.add((long)269); //invalid
        roomA.setEntranceIds(enterIds);

        ArrayList<Room> rooms = new ArrayList<Room>(); //put rooms in array and assign to adventure
        rooms.add(roomA); 
        rooms.add(roomB); 
        adv.setRooms(rooms); 

        adv.testInvalidEntranceRoomIds(); //test should throw exception

    }

    @Test(expected = Exception.class)
    public void testValidItemIds() throws Exception {
        System.out.println("Testing that a room having loot ids which dont belong to a real item throws an error");

        Room roomA = new Room();//set adventure's rooms 
        Room roomB = new Room();
        roomA.setId((long)100); //give them ids why not
        roomB.setId((long)101); 
        ArrayList<Room> rooms = new ArrayList<Room>(); 
        rooms.add(roomA); 
        rooms.add(roomB); 
        adv.setRooms(rooms); 

        Item itemA = new Item();//set adventure's items
        Item itemB = new Item(); 
        itemA.setId(423);//their ids are the valid ones
        itemB.setId(424);  
        ArrayList<Item> items = new ArrayList<Item>(); 
        items.add(itemA); 
        items.add(itemB); 
        adv.setItems(items); 

        ArrayList<Long> lootIds = new ArrayList<Long>();//set the first room's "loot"
        lootIds.add((long)423); //valid
        lootIds.add((long)9991); //invalid
        roomA.setLootIds(lootIds);

        adv.testInvalidLootItemIds();//test (should throw exception)
    }
}