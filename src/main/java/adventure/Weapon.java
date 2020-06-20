package adventure;

import org.json.simple.JSONObject;

public class Weapon extends Item implements Tossable {

    private static final long serialVersionUID = 1L;

    public Weapon(){
        super();
    }

    public Weapon(JSONObject weaponObj){
        super(weaponObj); 
    }

    /**
     * tossable
     * @return tossed message
     */
    @Override
    public String toss(){
        return this.getName() + " was tossed into the room"; 
    }

    
}
