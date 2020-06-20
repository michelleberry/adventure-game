package adventure;

import java.io.IOException;
import java.io.InputStream;
import java.io.File; 

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Container;
import java.awt.FlowLayout; 

public class AdventureView extends JFrame {

    private static final long serialVersionUID = 1055842908927931527L;

    private Adventure adventure;
    private Game game; 
    private Parser parser = new Parser(); 
    
    public static final int WIDTH = 430;
    public static final int HEIGHT = 550; 

    static final int OUTPUT_AREA_ROWS = 15; 
    static final int OUTPUT_AREA_COLS = 40;
    
    static final int INVENTORY_ROWS = 7; 
    static final int INVENTORY_COLS = 20; 

    static final int INPUT_AREA_COLS = 27; 

    private Container contentPane; 
    private JTextArea outputArea;
    private JTextArea inventoryDisplay; 
    private JLabel nameLabel;

    public AdventureView(Game myGame){
        super(); 
        setGame(myGame); 
        setAdventure(myGame.getAdventure()); 
        sizeSetUp(); 
        menuSetUp();
        setContentPane(); 
        setOutputArea(); 
        interactionSetUp();
        setInventoryDisplay(); 
    }

    /**
     * set the game variable
     * @param newGame
     */
    public void setGame(Game newGame){
        game = newGame; 
    }

    /**
     * set the adventure variable
     * handles event adventure is null
     * @param newAdventure
     */
    public void setAdventure(Adventure newAdventure){
        adventure = newAdventure; 
        if(adventure == null){
            startupLoadingError();
        }   
    }

//SETTING UP THE GUI:

    /**
     * sets basic characteristics of the jframe
     */
    private void sizeSetUp(){
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Adventure Game!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * sets up menupane
     */
    private void menuSetUp(){
        Container menuPane = getContentPane(); 
        menuPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        gameOptionsButtons(menuPane);
    }

    /**
     * sets the contentPane
     */
    private void setContentPane(){
        contentPane = getContentPane(); 
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        nameLabel = new JLabel("     Player Name: " + adventure.getPlayer().getName());
        contentPane.add(nameLabel);
    }

    /**
     * sets up the dialog box with a scrolling pane
     */
    private void setOutputArea(){
        outputArea = new JTextArea(OUTPUT_AREA_ROWS, OUTPUT_AREA_COLS);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        contentPane.add(scrollPane);
        outputArea.append(adventure.getCurrentRoom().getLongDescription()); 
    }
 
    /**
     * sets up the user input field and the enter button
     */
    private void interactionSetUp(){
        JTextField userInputString = new JTextField("you can type in here"); 
        userInputString.setColumns(INPUT_AREA_COLS);
        contentPane.add(userInputString); 
        setEnterButton(userInputString);
    }

    /**
     * creates four jbuttons with actions
     * @param menuPane
     */
    private void gameOptionsButtons(Container menuPane){
        JButton load = new JButton("Load Game"); 
        JButton save = new JButton("Save Game"); 
        JButton newAdv = new JButton("New Adventure");
        JButton playerName = new JButton("Change player name");
        playerName.addActionListener(listenA->changePlayerName()); 
        save.addActionListener(listenB->saveAs());
        newAdv.addActionListener(listenC->loadAdventure());
        load.addActionListener(listenD->loadSave());
        addGameOptionsButtons(load, save, newAdv, playerName, menuPane);  
    }

    /**
     * adds four jbuttons to the container in param
     * @param one
     * @param two
     * @param three
     * @param four
     * @param menuPane
     */
    private void addGameOptionsButtons(JButton one, JButton two, JButton three, JButton four, Container menuPane){
        menuPane.add(one); 
        menuPane.add(two); 
        menuPane.add(three);
        menuPane.add(four); 
    }

    /**
     * creates enter button with action, adds to container
     * @param userInputString
     */
    private void setEnterButton(JTextField userInputString){
        JButton but = new JButton("enter command");
        but.addActionListener(myListener->buttonPressed(userInputString));
        contentPane.add(but);  
    }

    /**
     * sets up the inventory label and text area display
     */
    private void setInventoryDisplay(){
        inventoryDisplay = new JTextArea(INVENTORY_ROWS, INVENTORY_COLS);
        JScrollPane scroller = new JScrollPane(inventoryDisplay);
        inventoryDisplay.setEditable(false);
        JLabel iTitle = new JLabel("Inventory Contents:");
        contentPane.add(iTitle);
        contentPane.add(scroller); 
        updateInventory();
    }

    /**
     * scrolls the Jtextarea to the very bottom of the field
     */
    public void scrollToBottom(){
        outputArea.setCaretPosition(outputArea.getText().length());
    }

//CONTROLLER METHODS:

    /**
     * enter button pressed method
     * @param field
     */
    private void buttonPressed(JTextField field){
        String userIn = field.getText(); 
        field.setText("");
        try{
            Command c = parser.parseUserCommand(userIn);
            String output = adventure.getPlayer().doCommand(c, adventure, game);
            outputArea.append(output);
        } catch (InvalidCommandException q){
            outputArea.append(q.getMessage());
        }
        refreshAfterButtonPressed();
    }

    /**
     * update text areas 
     */
    private void refreshAfterButtonPressed(){
        scrollToBottom();
        updateInventory();
    }

    /**
     * updates inventory display
     */
    private void updateInventory(){
        inventoryDisplay.setText("");
        inventoryDisplay.append(adventure.showInventory());
    }

    /**
     * clear and reset output area
     */
    private void refreshOutputArea(){
        outputArea.setText("");
        outputArea.append(adventure.getCurrentRoom().getLongDescription());
    }

    /**
     * call all refresh methods meant to update interface
     */
    private void refreshInterface(){
        updateInventory(); 
        refreshOutputArea();
        updatePlayerName(adventure.getPlayer().getName());
    }

    /**
     * informs the user of an error and asks for further action
     */
    private void newAdvError(){
        int input = JOptionPane.showConfirmDialog(null, "File opening failed. Would you like to try again?");   
        if(input == JOptionPane.YES_OPTION){
            loadAdventure();
        }  
    }

    /**
     * informs the user of an error and asks for further action
     */
    private void loadError(){
        int input = JOptionPane.showConfirmDialog(null, "File opening failed. Would you like to try again?");   
        if(input == JOptionPane.YES_OPTION){
            loadSave();
        } 
    }

    /**
     * handle failure of command line switch file loading
     */
    private void startupLoadingError(){
        int input = JOptionPane.showConfirmDialog(null, 
        "File opening failed. Try again? If no, default adventure will be loaded.");
        if(input == JOptionPane.YES_OPTION){
            //load json or serial checking game's instance vars
            loadFileTypeFromArgs(); 
        } else if (input == JOptionPane.NO_OPTION){
            loadDefaultAdventure();
        } else {
            System.exit(0); 
        }
    }

    /**load json or serial checking game's instance vars */
    private void loadFileTypeFromArgs(){
        if(game.getLoadSave()){
            loadSaveOnStartup();
        } else {
            loadAdventureOnStartup();
        }
    }

    /**
     * asks user to choose a file and returns the name of it
     * @return the filename
     */
    private String getFileName(){
        String fileName = null; 
        JFileChooser fileChooser = new JFileChooser(); 
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File readFile = fileChooser.getSelectedFile(); 
            fileName = readFile.getPath(); 
        } 
        return fileName; 
    }

    /**
     * opens a new adventure and refreshes gui to reflect that
     */
    private void loadAdventure(){
        String fileName = getFileName(); 
        
        Adventure tempAdv = game.generateAdventure(game.loadAdventureJson(fileName));
        if(tempAdv != null){
            adventure = tempAdv;
            game.setTheAdventure(adventure); 
            refreshInterface();
        } else {
            newAdvError(); 
        }
    }

    /**
     * opens a new adventure without refreshing gui
     * as at the point this method is called it hasnt been initialized 
     */
    private void loadAdventureOnStartup(){
        String fileName = getFileName(); 
        
        Adventure tempAdv = game.generateAdventure(game.loadAdventureJson(fileName));
        if(tempAdv != null){
            adventure = tempAdv; 
            game.setTheAdventure(adventure);
        } else {
            startupLoadingError(); 
        } 
    }

    /**
     * loads a saved adventure and refreshes gui to reflect that
     */
    private void loadSave(){
        String fileName = getFileName(); 

        Adventure tempAdv = game.deSerialize(fileName);
        if(tempAdv != null){
            adventure = tempAdv; 
            game.setTheAdventure(adventure); //update game instance vars
            refreshInterface();
        } else {
            loadError(); 
        }
    }

    /**
     * opens a saved adventure without refreshing gui
     * as at the point this method is called it hasnt been initialized 
     */
    private void loadSaveOnStartup(){
        String fileName = getFileName(); 

        Adventure tempAdv = game.deSerialize(fileName);
        if(tempAdv != null){
            adventure = tempAdv; 
        } else {
            startupLoadingError(); 
        }
    }

    /**
     * initializes adventure variable as the default adventure.
     */
    private void loadDefaultAdventure(){
        InputStream inputStream = Game.class.getClassLoader().getResourceAsStream("michelles_adventure.json");
        adventure = game.generateAdventure(game.loadAdventureJson(inputStream));
        game.setTheAdventure(adventure);
    }

    /**
     * asks for a name to save game as and saves it
     */
    private void saveAs(){
        String saveName = JOptionPane.showInputDialog("Please enter a name to save the game as");
        if(saveName != null){
            adventure.getPlayer().setSaveGameName(saveName);
            try{
                String tellUser = game.serializeAdventure(adventure);
                outputArea.append(tellUser); 
            } catch (IOException io) {
                outputArea.append(io.getMessage());
            }
        }

    }

    /**
     * changes player name and updates in the gui
     */
    private void changePlayerName(){
        String nameInput = JOptionPane.showInputDialog( "Please enter new Player name");
        updatePlayerName(nameInput);
    }

    /**
     * updates the player name in the gui
     * @param newName
     */
    private void updatePlayerName(String newName){
        if(newName != null){
            adventure.getPlayer().setName(newName);
            nameLabel.setText("     Player Name: " + adventure.getPlayer().getName());
        }
    }

    /**
     * asks to save game before quitting program
     */
    public void quitSaveInquiry(){
        //pop up asks: would you like to save before quitting?
        int input = JOptionPane.showConfirmDialog(null, "Would you like to save before exiting?");   
        //if yes, open up save popup
        if(input == JOptionPane.YES_OPTION){
            saveAs();
        } 
        //then quit
        if(input != JOptionPane.CANCEL_OPTION){
            System.exit(0);
        }  
    }

    public static void main(String[] args){ 
         
        Game theGame = new Game();

        theGame.parseCmdLineArgs(args);  
        if(theGame.loadStartAdventure() == null){
            theGame.setTheAdventure(null); 
        } else{
            theGame.generateStartAdventure(theGame.loadStartAdventure());
        }
                
        AdventureView gameFrame = new AdventureView(theGame); 
        gameFrame.setVisible(true);

        theGame.setGameView(gameFrame); 
    }
    

}
