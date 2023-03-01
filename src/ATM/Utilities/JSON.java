package ATM.Utilities;

import ATM.Bank.Transaction;
import ATM.Constants.Constants;
import org.json.*;

import java.util.ArrayList;
import java.util.logging.Level;

public class JSON {
    private final JSONObject jsonObject = new JSONObject();
    public JSON(String type){
        this.jsonObject.put(Constants.JSON.Type, type);
    }

    public <T> JSON add(String key, T t){
        try{
            this.jsonObject.put(key, t);
        } catch (JSONException e) {
            LogHelper.log(Level.SEVERE, "Error when adding JSON string.", e);
        }
        return this;
    }

    public static JSONObject tryParse(String input) {
        JSONObject jo = null;
        try {
            jo = new JSONObject(input);
        } catch (JSONException e) {
            LogHelper.log(Level.SEVERE, "Failed to parse string [" + input + "] to JSON.", e);
        }
        return jo;
    }

    public String toString(){
        return this.jsonObject.toString();
    }

    public static String parseTransactionsToString(ArrayList<Transaction> transactions) {
        return parseTransactions(transactions).toString();
    }

    public static JSONArray parseTransactions(ArrayList<Transaction> transactions) {
        return new JSONArray(transactions);
    }
}
