package adventure;

import org.json.simple.JSONObject;

public class BrandedClothing extends Clothing implements Readable {

    private static final long serialVersionUID = 1L;

    public BrandedClothing(){
        super(); 
    }

    public BrandedClothing(JSONObject brandedClothingObj){
        super(brandedClothingObj);
    }

    /**
     * @return read message
     */
    @Override
    public String read(){
        return "\n" + this.getName() + ": " + this.getDescription() + "\n"; 
    }

    /**
     * @return wear message
     */
    @Override
    public String wear(){
        setWearing(true); 
        return "\nYou are now wearing " + this.getName() + "\n"; 
    }
    
}
