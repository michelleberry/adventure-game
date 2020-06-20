package adventure;

import org.json.simple.JSONObject;

public class Clothing extends Item implements Wearable{

    private boolean wearing = false; 

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Clothing(){
        super(); 
    }

    public Clothing(JSONObject clothingObj) {
        super(clothingObj);
    }

    /**
     * sets wearing true and return message
     * @return wearing message
     */
    @Override
    public String wear(){
        setWearing(true);  
        return "\nYou are now wearing " + this.getName() + ".\n"; 
    }

    /**
     * set wearing
     * @param state true or false
     */
    public void setWearing(boolean state){
        wearing = state; 
    }

    /**
     * @return wearing
     */
    public boolean getWearing(){
        return wearing; 
    }
    
}
