package adventure;

import org.json.simple.JSONObject;

public class SmallFood extends Food implements Tossable{

    private static final long serialVersionUID = 1L;
    
    public SmallFood(){
        super(); 
    }

    public SmallFood(JSONObject smallFoodObj){
        super(smallFoodObj); 
    }

    /**
     * @return eat message
     */
    @Override
    public String eat(){
        return "\nYou ate " + this.getName() + ".\n";
    }

    /**
     * @return toss message
     */
    @Override
    public String toss(){
        return "\nYou tossed " + this.getName() + " into the room\n";
    }
}
