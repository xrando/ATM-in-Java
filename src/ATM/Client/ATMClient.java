package ATM.Client;

import ATM.Constants.Constants;
import ATM.Utilities.JSON;
import org.json.JSONObject;

public class ATMClient {
    public static void main(String[] args){
        Client client = new Client();
        //can have multiple listen events here
        //each listen event will return a string from server
/*        while (true){
            String returnMsg = null;
            if (returnMsg.equals(Constants.Stream.EOS))
                break;

        }*/

        //These codes are for testing, actual implementation will be with UI

        //User user = new User();

        // Create a new user
        //user.CreateUser();

        //System.out.print(client.listen(new JSON(Constants.User.CreateUser).add(Constants.User.Username, "abcd").add(Constants.User.Password, "123123").toString()));

        //logout
        //System.out.println(client.listen(new ATMRequestBuilder(Constants.User.Logout).toString()));

        // Create User
        //JSONObject createUserTest = new JSONObject(client.listen(new JSON(Constants.User.CreateUser).add(Constants.User.Username, "abc123").add(Constants.User.Password, "123123").toString()));

        JSONObject jo = new JSONObject(client.listen(new JSON(Constants.User.Login).add(Constants.User.Password, "258852").add(Constants.User.Username, "test").toString()));

        //forget pin
        //JSONObject ForgetPin = new JSONObject(client.listen(new JSON(Constants.User.ForgetPin).add(Constants.User.Username, "test").toString()));

        // Change Pin
        JSONObject ChangePin = new JSONObject(client.listen(new JSON(Constants.User.ChangePin).add(Constants.User.oldPin, "258852").add(Constants.User.newPin, "123123").toString()));

        JSONObject jo1 = new JSONObject(client.listen(new JSON(Constants.Account.SelectAccount).add(Constants.Account.SelectedAccount, "0").toString()));
        JSONObject jo2 = JSON.tryParse(client.listen(new JSON(Constants.Account.TransactionHistory).toString()));

        //method 1:
        //System.out.println(jo.get(Constants.JSON.Type));
        //System.out.println(jo.get(Constants.User.LoginStatus));

        //System.out.println(ChangePin.get(Constants.User.ChangePin));
        System.out.println(jo1.get(Constants.JSON.Type));
        System.out.println("Account Selected: "+jo1.get(Constants.Account.SelectedAccount));
        //method 2:
        //for(var a:jo.names()){
        //    System.out.println(jo.get(a.toString()));
        //}

        //Logout
        JSONObject logout = new JSONObject(client.listen(new JSON(Constants.User.Logout).toString()));

        client.close();
    }
}
