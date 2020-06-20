| method sig | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
|Player()| initialize |none|setName(), setSaveGameName(), setCurrentRoom()|none| 3 lines|
| void setName(String newName) | sets the name instance variable | name | none | none | 1 line |
| void setCurrentRoom(Room room) | set current room as parameter | currentRoom | none | none | 1 line |
| void setSaveGameName(String newSaveGameName)| set the save game name for the player | saveGameName | none | none | 1 line |
| void  setInventory(ArrayList<Item> newInventory)| set the inventory for the player | inventory | none | none | 1 line |
| String getName() | return player name | name | none| none | 1 line |
| ArrayList<Item> getInventory() | return list of inventory items | inventory | none | none | 1 line |
| Room getCurrentRoom() | return the current room | currentRoom | none | none | 1 line |
| String getSaveGameName() | return the save game name | saveGameName | none | none | 1 line |
| void addToInventory(Item newItem) | adds item given as parameter to player inventory | inventory | none | none | 1 line |
| void removeFromInventory(Item toRemove) | removes item given as parameter from player inventory | inventory | none | none | 1 line |
| String doCommand(Command c, Adventure adv, Game game, Scanner scnr) | check if command is inventory or quit and execute. if it isnt , trigger doCommandTwo, return output string | none | Adventure.showInventory(), Game.saveAdventure(), doCommandTwo() | Adventure, Game | 9 lines|
| String doCommandTwo(Command c, Adventure adv, Game game)| checks if command is look, take, or go and trigger corresponding method, otherwise, doCommandThree() | none | Adventure.executeLook(), Adventure.executeGo(), Adventure.executeTake(), doCommandThree() | Adventure | 9 lines |
| String doCommandThree(Command c, Adventure adv, Game game)| checks if command is eat,toss,read,or wear and trigger corresponding method | none | Adventure.eatFood(), Adventure.tossItem(), Adventure.readItem(), Adventure.wearClothing() | none | 10 lines |
| String toString()| return String representation of player | saveGameName, name, currentRoom | other class methods called | objects used with method calls | 1 line |