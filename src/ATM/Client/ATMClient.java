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

        //login
        //System.out.print(client.listen(new JSON(Constants.User.Login).add(Constants.User.Username, "test").add(Constants.User.Password, "123123").toString()));
        //System.out.print(client.listen(new JSON(Constants.User.Login).add(Constants.User.Password, "123123").add(Constants.User.Username, "test").toString()));
        //System.out.println(client.listen(new ATMRequestBuilder(Constants.User.Login).add("test").add("123123").toString()));



        //System.out.print(client.listen(new JSON(Constants.User.ChangePin).add(Constants.User.oldPin, "123456").add(Constants.User.newPin, "123456").toString()));


        //System.out.print(client.listen(new JSON(Constants.User.Logout).toString()));
        //logout
        //System.out.println(client.listen(new ATMRequestBuilder(Constants.User.Logout).toString()));

        JSONObject jo = new JSONObject(client.listen(new JSON(Constants.User.Login).add(Constants.User.Password, "123123").add(Constants.User.Username, "test").toString()));
        JSONObject jo1 = new JSONObject(client.listen(new JSON(Constants.Account.SelectAccount).add(Constants.Account.SelectedAccount, "0").toString()));

        //method 1:
        System.out.println(jo.get(Constants.JSON.Type));
        System.out.println(jo.get(Constants.User.LoginStatus));
        System.out.println(jo1.get(Constants.JSON.Type));
        System.out.println("Account Selected: "+jo1.get(Constants.Account.SelectedAccount));
        //method 2:
        for(var a:jo.names()){
            System.out.println(jo.get(a.toString()));
        }
        //Retrieve Transaction History
        JSONObject jo2 = JSON.tryParse(client.listen(new JSON(Constants.Account.TransactionHistory).toString()));
        System.out.println("Retrieve Transaction History: "+jo2.get(Constants.Account.TransactionHistory));

        //Transaction (Deposit) Test
        JSONObject jo4 = new JSONObject(client.listen(new JSON(Constants.Transaction.Deposit).add(Constants.Transaction.Amount, 1000).add(Constants.Transaction.TransactionNote, "Deposit").toString()));
        System.out.println(jo4.get(Constants.JSON.Type));
        System.out.println("Deposit : "+jo4.get(Constants.Transaction.Deposit));
        System.out.println("Balance : "+jo4.get(Constants.Account.CheckBalance));

        //Transaction (Withdrawal) Test
        JSONObject jo5 = new JSONObject(client.listen(new JSON(Constants.Transaction.Withdraw).add(Constants.Transaction.Amount, 500).add(Constants.Transaction.TransactionNote, "Withdrawal").toString()));
        System.out.println(jo5.get(Constants.JSON.Type));
        System.out.println("Withdrawal : "+jo5.get(Constants.Transaction.Withdraw));
        System.out.println("Balance : "+jo5.get(Constants.Account.CheckBalance));


        client.close();
    }
}
