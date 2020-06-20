| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|Item()| initialize empty item | none |setId(), setName(), setLongDescription(), setItemInside()| none | 4 lines|
| Item(JSONObject itemObject) | initialize to values in JSONObject w/corresponding keys | none | setId(), setName(), setLongDescription(), setItemInside() | none | 4 lines |
| void setId(long newId) | set Item id | id | none | none | 1 line |
| void setName(String newName) | set item name | name | none | none | 1 line |
| void setDescription(String newDesc)| set item description | description | none | none | 1 line |
| void setLongDescription(String newDesc)| set item description | none | setDescription() | none | 1 line |
| void setItemInside(Room room) | set the room containing the item | itemInside | none | none | 1 line |
| long getId() | return ID of item | id | none | none | 1 line |
| String getName() | return name | name | none | none | 1 line |
| String getDescription()| return description | description | none | none | 1 line |
| String getLongDescription()| return description | none | none | getDescription() | 1 line |
| Room getContainingRoom() | return a reference to the room that contains the item  | itemInside | none | none | 1 line |
| void setRoomContainingItem(ArrayList<Room> allRooms)| finds the room with a loot id matching that of this item, calls setItemInside to set that room as itemInside | none| setItemInside() | Room | 9 lines |
| String toString() | return string representation of item | name, description, id | none | none | 1 line |