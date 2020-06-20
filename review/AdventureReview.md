| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|Adventure()| initialize |Room currentRoom|setCurrentRoom()|none| 1 line|
| Adventure(JSONArray roomsJsonArray, JSONArray itemsJsonArray) throws Exception | call methods to set instance vars in required order | ArrayList of items, Arraylist of rooms | setAllItems(), setAllRooms(), Room.setConnectedRooms(), Item.setRoomContainingItem(), setStart(), roomJsonTesting() | JsonArray, Arraylists | 10 lines  |
| void roomJsonTesting() throws Exception | calls all tests of the values in the json files | none | testValidRoomConnections(), testInvalidEntranceRoomIds(), testInvalidLootItemIds() | none | 3 lines|
| ArrayList<Long> getValidRoomIds()| return a list of ids derived from all rooms | rooms | none | none | 5 lines |
| void testInvalidEntranceRoomIds() throws Exception | make sure all entrance from json file has valid room id | rooms | Room.getEntranceIds(), getValidRoomIds() | room | 9 lines |
| ArrayList<Long> getValidItemIds() | return a list of ids derived from all items | items | none | none | 5 lines |
|void testInvalidLootItemIds() throws Exception | make sure all loot in json file has valid id | rooms | getValidItemIds(), Room.getLootIds() | Room | 9 lines |
| void testValidRoomConnections() throws Exception | ensure there are no errors in json file w/ room connections | none | ensureAllRoomsHaveExits(), ensureAllRoomExitsCorrelate() | none | 2 lines |
| void ensureAllRoomsHaveExits() throws Exception | check every room using ensureRoomHasExits() | rooms | ensureRoomHasExits()  | none | 3 lines |
| void ensureRoomHasExits(Room toTest) throws Exception | ensure the room passed as parameter has at least one exit, or throw exception | none | Room.getConnectedRoom | Room | 10 lines |
| void ensureAllRoomExitsCorrelate() throws Exception | checks all rooms with valid directions with with ensureRoomExitsCorrelate() | rooms | ensureRoomExitsCorrelate() | none | 5 lines |
| void ensureRoomExitsCorrelate(Room room, String[] goDirs, String[] leaveDirs) throws Exception | ensures that a room have correct double sided connection | none | Room.getConnectedRoom() | Room | 7 lines |
| void setAllRooms(JSONArray roomsJsonArray) | Initializes rooms arraylist | rooms, items | Room class constructor | Room, Arraylist<Item> | 5 lines|
| void setAllItems(JSONArray itemsJsonArray) | Initializes items arraylist | instance vars used | Item class constructor | Item | 5 lines |
| Item setItemType(JSONObject someItem) | check if item is edible or wearable, else trigger method that checks remaining types | none | setFoodItemType(), setClothingItemType(), setItemTypeTwo() | Item | 9 lines |
| Item setItemTypeTwo(JSONObject someItem) | check if item is weapon or spell to create / return item of that type | none | Weapon() and Spell() constructors | JSONObject | 9 lines |
| Item setFoodItemType(JSONObject someItem) | check if an edible item is SmallFood or Food | none | SmallFood() and Food() constructors | JSONObject | 4 lines |
| Item setClothingItemType(JSONObject someItem) | if item is wearable, check if BrandedClothing or Clothing and return instance of item | none | BrandedClothing() and Clothing() constructors | JSONObject | 4 lines |
| void setStart() | look for the room with start = true, set as the current room | currentRoom, rooms | setCurrentRoom() | Room | 6 lines |
| void setCurrentRoom(Room room) | sets the reference to Room currentRoom | currentRoom | none | Room | 1 line |
| void setPlayer(Player newPlayer) | set the player for the adventure | player | none | none | 1 line |
| void setRooms(ArrayList<Room> newRooms) | set the rooms for the adventure | rooms | none | none | 1 line |
| void setItems(ArrayList<Room> newItems) | set the items for the adventure | items | none | none | 1 line |
| ArrayList<Room> listAllRooms() | return a list of all rooms in game | rooms | none | none | 1 line |
| ArrayList<Item> listAllItems() | return a list of all items in game | items | none | none | 1 line |
| String getCurrentRoomDescription() | return description of currentRoom | currentRoom | Room.getShortDescription(), Room.getLongInfo() | Room | 1 line |
| Room getCurrentRoom() | return currentRoom | currentRoom | none | Room | 1 line |
| Player getPlayer() | return player | instance vars used | none  | Player | 1 line |
| String executeGo(String direction) | move player to room in desired direction if possible. return output string for user | currentRoom | setCurrentRoom(), getConnectedRoom()- from Room class| Room | 10 lines |
| String executeLook(String theItem) | check if the item is blank or not, and return strings based on that | currentRoom | findItemtoLookAt(theItem) | Item, String | 7 lines|
| String findItemToLookAt(String theItem | see if item is available to look at and return a string | none | Item.getDescription(), getItemFromName(), isAnItem(), iteminRoom(), itemInInventory() | Item, String | 4 lines |
| String executeTake(String theItem) | check if String theItem is null or not, return output string | none | findItemToTake(), takeItem() | String | 7 lines |
| String findItemToTake(String theItem) | if item can be taken, return the item, otherwise null | none |isAnItem(), itemInRoom(), getItemFromName() | none | 4 lines|
| String takeItem(String theItemName) | if item can be taken, take it, return output message telling what happened | player | findItemToTake(), Player.addToInventory() , Room.removeItemFromRoom(), Item.getName() | Item, Room, Player | 8 lines|
| boolean isAnItem(String itemName) | returns true if an item with name itemName exists | items | Item.getName() | Item | 6 lines |
| Item getItemFromName(String itemName) | returns item with name itemName if item is an existing item in the adventure | items | Item.getName() | Item | 6 lines |
| boolean itemInInventory(String itemName) | check if item is in player inventory, return true or false | player | Item.getName(), Player.getInventory() | Item, Player | 7 lines of code |
| boolean itemInRoom(String itemName) | check if an item is in currentRoom, return true or false | currentRoom | Room.listItems() | Room | 6 lines |
| String showInventory() | returns a string formatted list of the inventory's contents | player | Player.getInventory(), itemDisplay() | Player, Item | 10 lines |
| String itemDisplay(Item i) | return item name and wearing:true/false if item is an instance of clothing | none | Item.getName(), Clothing.getWearing() | Item, Clothing | 5 lines |
| Food findItemToEat(String itemName) | check if item is valid and can be eaten, if it is return it | none | itemInInventory(), isAnItem(), getItemFromName() | String | 10 lines |
| String eatFood(String toEat) | if food is valid to eat, remove from adventure | inone | findItemToEat(), Player.removeFromInventory(), Food.eat() | Player, Food | 6 lines|
| Item findItemToToss(String toToss) | see if item can be tossed, and if so return that item | none | isAnItem(), itemInInventory(), getItemFromName() | String | 9 lines |
| String tossItem(String toToss) | toss item if it can be tossed | none | findItemToToss(), Player.removeFromInventory(), Room.addItemToRoom(), Weapon.toss()/SmallFood.toss() | Player, Room, Weapon/SmallFood | 10 lines |
| Item findItemToRead(String toRead) | see if item can be read and return the item | none | itemInInventory(), isAnItem(), itemInRoom(), getItemFromName() | String | 9 lines |
| String readItem(String toRead) | read item if it can be read | none | findItemToRead(), Spell.read(), BrandedClothing.read() | Spell, BrandedClothing | 9 lines |
| Clothing findClothingToWear(String toWear) | return item with name toWear if item is clothing and in inventory | none | isAnItem(), itemInInventory(), getItemFromName() | String | 9 lines |
| String wearClothing(String toWear) | wear clothing if it can be worn | none | findClothingToWear(), Clothing.wear() | Clothing | 5 lines|
| String toString() | return string representation of adventure | player | Player.getSaveGameName() | Player | 1 line |