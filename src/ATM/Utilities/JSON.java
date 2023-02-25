package ATM.Utilities;

import ATM.Constants.Constants;
import org.json.*;

public class JSON {
    private final JSONObject jsonObject = new JSONObject();
    public JSON(String type){
        this.jsonObject.put(Constants.JSON.Type, type);
    }

    public <T> JSON add(String key, T t){
        this.jsonObject.put(key, t);
        return this;
    }

    public String toString(){
        return this.jsonObject.toString();
    }
}
