package adventure;

import org.json.simple.JSONObject;

public class Food extends Item implements Edible {

    private static final long serialVersionUID = 1L;

    public Food(){
        super(); 
    }

    public Food(JSONObject foodObj){
        super(foodObj); 
    }

    /**
     * @return eat message
     */
    @Override
    public String eat(){
        //remove item from inventory when eaten
        return ("\nYou ate " + this.getName() + ". Nom nom nom"); 
    }
}
