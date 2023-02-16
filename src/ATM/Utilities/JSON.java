package ATM.Utilities;

import ATM.Constants.Constants;
import org.json.*;

public class JSON {
/*    private JSONArray ja = new JSONArray();
    public JSON(String type){
        this.ja.put(0, type);
    }

    public <T> JSON add(T t){
        this.ja.put(t);
        return this;
    }*/
    private JSONObject jsonObject = new JSONObject();
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
