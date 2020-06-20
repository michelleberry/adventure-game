package adventure;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;


public class RoomTest{
    private Room testRoom;

@Before
public void setup(){
    testRoom = new Room (); 
    testRoom.setConnectedRooms(); 

}

@Test
public void testSetNameWithValidInput(){
    System.out.println("Testing setName with valid name");
    String roomName = "one";
    testRoom.setName(roomName);
    assertTrue(testRoom.getName().equals(roomName));

}

@Test
public void testRoomConnectOne(){
    System.out.println("Testing room connection with valid direction (up) comparing names");
    Room upstairs = new Room(); 
    upstairs.setName("Attic");
    testRoom.setName("my room");
    testRoom.setConnectedRoom("up", upstairs);
    assertTrue(testRoom.getConnectedRoom("up").getName().equals("Attic"));
    /**
     * ASSUMPTION: setConnectedRoom sets one exit, it is not automatically setting a double sided connection. 
     */
}

@Test
public void testRoomConnectTwo(){
    System.out.println("Testing double room connection with valid direction comparing names");
    Room upstairs = new Room(); 
    upstairs.setName("Attic");
    testRoom.setName("my room");
    testRoom.setConnectedRoom("up", upstairs);
    upstairs.setConnectedRoom("down", testRoom);
    assertTrue(upstairs.getConnectedRoom("down").getName().equals("my room"));
    /**
     * ASSUMPTION: setConnectedRoom sets one exit, it is NOT automatically setting a double sided connection. 
     */
}

@Test
public void testRoomConnectViaId(){
    System.out.println("Testing room connections comparing room IDs");
    Room adjacent = new Room(); 
    adjacent.setId(1707);
    testRoom.setId(1706); 
    testRoom.setConnectedRoom("W", adjacent);
    assertTrue(testRoom.getConnectedRoom("W").getId() == (long) 1707);
}

@Test
public void testRoomConnectViaDescription(){
    System.out.println("Testing room connections comparing room descriptions");
    Room adjacent = new Room(); 
    adjacent.setLongDescription("a dusty room");
    testRoom.setConnectedRoom("down", adjacent);
    assertTrue(testRoom.getConnectedRoom("down").getLongDescription().equals(adjacent.getLongDescription())); 
}

@Test
public void testRoomConnectReferences(){
    System.out.println("Testing room connections comparing reference to room");
    Room adjacent = new Room(); 
    testRoom.setConnectedRoom("up", adjacent);
    assertTrue(testRoom.getConnectedRoom("up").equals(adjacent));
}

@Test
public void testInvalidRoomConnection(){
    System.out.println("Testing getConnectedRoom() returns null given invalid direction"); 
    /*nothing is currently connected to our testRoom */
    assertFalse(testRoom.getConnectedRoom("blah blah blah") != null); 
}

}