## Class Room

| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|Room()| initialize empty/blank room instance vars | none |setID(), setName(), setShortDescription(), setLongDescription(), setConnectedRooms()|none| 5 lines |
| Room(JSONObject roomObject) | initialize basic instance vars | none | setID(), setName(), setShortDescription(), setLongDescription(), setConnectedRooms(), setStartIfPresent() | JSONObject | 6 lines |
| Room(JSONObject roomObject, ArrayList<Item> allItems) | initialize all instance vars | none in this exact method | Room(JSONObject roomObject), placeLootInRoom(), storeEntranceIdsDirs() | ArrayList, JSONObject| 9 lines |
| void storeEntranceIdsDirs(JSONArray entranceList) | store info (id's and directions) of "entrance" objects in arraylists | ArrayList entranceIDs, ArrayList entranceDirs | none | JSONArray | 5 lines|
| Room findRoomFromId(long thisId, ArrayList<Room> allRooms) | return room reference given id | none | getId() | none | 7 lines |
| void placeLootInRoom(JSONArray lootList, ArrayList<Item> allItems) | get loot id from jsonarray and send id to other func to be placed in room | none | findLootToPlace() | ArrayList<Item> | 6 lines |
| void findLootToPlace(ArrayList<Item> allItems, long searchId) | if searchId is that of a valid item, add item to room | none | none | none | 5 lines |
| void setStartIfPresent(JSONObject roomObject) | checks if JSONObject contains start and sets start value | none | setStart() | String | 5 lines|
| void setId(long newId) | sets room id as parameter| id| none | none | 1 line |
| void setStart(String toSet) | sets start as parameter| start| none | none | 1 line |
| void setName(String newName) | sets name as parameter| name| none | none | 1 line |
| public void setShortDescription(String newShDesc) | sets shortDescription as parameter| shortDesription| none | none | 1 line |
| public void setLongDescription(String newLongDesc) | sets longDescription as parameter| longDescription | none | none | 1 line |
| void setConnectedRooms() | initializes connectedRooms map with valid directions | connectedRoom | none | none | 6 lines |
| void setConnectedRooms(ArrayList<Room> allRooms) | sets the references of rooms connected in all direction to this room | entranceDirs, entranceIds | findRoomFromId(), setConnectedRoom() | Room | 6 lines |
| void setConnectedRoom(String dir, Room toConnect) | sets a pair for connectedRoom only if direction is valid | HashMap connectedRooms | none | none | 7 lines|
| void setItems(ArrayList<Item> newItems) | set items | items | none | none | 1 line |
| void setEntranceIds(ArrayList<Long> newIds) | set entrance ids| entranceIds | none | none | 1 line |
| void setEntranceDirs(ArrayList<String> newDirs) | set entranceDirs | entranceDirs | none | none | 1 line |
| void setLootIds(ArrayList<Long> newLootIds) | set loot ids | lootIds | none | none | 1 line |
| void removeItemFromRoom(Item toRemove) | removes item from room's list of item | items | none | none | 1 line |
| void addItemToRoom() | adds item to room's list of items | items | none | none | 1 line|
| long getId() | return the id number | id | none | none | 1 line |
| String getStart() | return value of start string | start | none | none | 1 line |
| ArrayList<Item> listItems() | return list of items inside the room | items | none | none | 1 line |
| String listOfItems() | returns string formatted list of items in current room | items | none | none | 8 lines |
| String getName() | return the name | name | none | none | 1 line |
| String getShortDescription() | return the short description | shortDescription | none | none | 1 line |
| String getLongDescription() | return the long description | longDescription | none | none | 1 line |
| String getLongInfo() | return the long description + list of items | longDescription | listOfItems() | none | 1 line |
| Room getConnectedRoom(String direction) | returns connected room in given direction if valid | connectedRooms| none | none | 5 lines|
| ArrayList<Long> getEntranceIds() | return entrance ids| entranceIds | none | none | 1 line |
| ArrayList<String> getEntranceDirs() | return entranceDirs | entranceDirs | none | none | 1 line |
| ArrayList<Long> getLootIds() | return loot ids arraylist | lootIds | none | none | 1 line |
| String toString() | return string representation of room object | name, id, longDescription | none | none | 1 line |






