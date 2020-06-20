package adventure;

import org.json.simple.JSONObject;

public class Spell extends Item implements Readable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Spell() {
        super(); 
    }

    public Spell(JSONObject spellObj){
        super(spellObj);
    }

    /**
     * return description as if reading item
     * @return description 
     */
    @Override
    public String read(){
        return "you read " + this.getName() + ": " + this.getDescription(); 
    }
}
