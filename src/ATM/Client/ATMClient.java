package ATM.Client;

import ATM.Constants.Constants;
import ATM.Utilities.JSON;
import org.json.JSONArray;
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
        //JSONObject createUserTest = new JSONObject(client.listen(new JSON(Constants.User.CreateUser).add(Constants.User.Username, "abc123").add(Constants.User.Password, "123123").add(Constants.User.Email, "pureinc933@gmail.com").toString()));

        JSONObject jo = new JSONObject(client.listen(new JSON(Constants.User.Login).add(Constants.User.Password, "123123").add(Constants.User.Username, "test").toString()));

        //forget pin
        //JSONObject ForgetPin = new JSONObject(client.listen(new JSON(Constants.User.ForgetPin).add(Constants.User.Username, "test").toString()));

        // Change Pin
        //JSONObject ChangePin = new JSONObject(client.listen(new JSON(Constants.User.ChangePin).add(Constants.User.oldPin, "123456").add(Constants.User.newPin, "123123").toString()));

        //Get User Info
        JSONObject GetUserInfo = JSON.tryParse(client.listen(new JSON(Constants.User.GetUserInformation).toString()));

        //Select User Account
        JSONObject SelectAccount = JSON.tryParse(client.listen(new JSON(Constants.Account.SelectAccount).add(Constants.Account.SelectedAccount, 0).toString()));

        //Update User Object
        //JSONObject UpdateUser = JSON.tryParse(client.listen(new JSON(Constants.User.UpdateUser).add(Constants.User.Email, "test@test.com").add(Constants.User.Phone, "0").add(Constants.User.Username, "test").toString()));

        //method 1:
        System.out.println(jo.get(Constants.JSON.Type));
        System.out.println(jo.get(Constants.User.LoginStatus));
        System.out.println(jo);

        // Get User Info
        System.out.println(GetUserInfo.get(Constants.User.Username));
        System.out.println(GetUserInfo.get(Constants.User.Phone));
        System.out.println(GetUserInfo.get(Constants.User.Email));

        //System.out.println(ChangePin.get(Constants.User.ChangePin));
        System.out.println(SelectAccount.get(Constants.JSON.Type));
        System.out.println("Account Selected: "+SelectAccount.get(Constants.Account.SelectedAccount));

        //method 2:
        jo.names().forEach(name -> System.out.println(jo.get(name.toString())));

        //Retrieve Transaction History
        JSONObject jo2 = JSON.tryParse(client.listen(new JSON(Constants.Account.TransactionHistory).toString()));

        //System.out.println("Retrieve Transaction History: "+jo2.get(Constants.Account.TransactionHistory));
        JSONArray ja = new JSONArray(jo2.get(Constants.Account.TransactionHistory).toString());
        ja.forEach(record -> {
            JSONObject joo = new JSONObject(record.toString());
            System.out.print(Constants.Transaction.TransactionNote + " : " + joo.get(Constants.Transaction.TransactionNote) + "\t");
            System.out.print(Constants.Transaction.Amount + " : " + joo.get(Constants.Transaction.Amount) + "\t");
            System.out.print(Constants.Transaction.date + " : " + joo.get(Constants.Transaction.date) + "\n");
            System.out.print(Constants.Transaction.TimeStamp + " : " + joo.get(Constants.Transaction.TimeStamp) + "\n");
        });

        //Retrieve All User Accounts
        JSONObject retrieveAccounts = JSON.tryParse(client.listen(new JSON(Constants.Account.AllAccounts).toString()));
        JSONArray ja2 = new JSONArray(retrieveAccounts.get(Constants.Account.AllAccounts).toString());
        ja2.forEach(record -> {
            JSONObject joo2 = new JSONObject(record.toString());
            System.out.print(Constants.Account.AccountId + " : " + joo2.get(Constants.Account.AccountId) + "\t");
            System.out.print(Constants.Account.AccountType + " : " + joo2.get(Constants.Account.AccountType) + "\t");
            System.out.print(Constants.Account.UserID + " : " + joo2.get(Constants.Account.UserID) + "\n");
        });



        //Transaction (Deposit) Test
        JSONObject jo4 = new JSONObject(client.listen(new JSON(Constants.Transaction.Deposit).add(Constants.Transaction.Amount, 1000).add(Constants.Transaction.TransactionNote, "Deposit").toString()));
        System.out.println(jo4.get(Constants.JSON.Type));
        System.out.println("Deposit : "+jo4.get(Constants.Transaction.Deposit));
        System.out.println("Balance : "+jo4.get(Constants.Account.GetAccountBalance));

        //Transaction (Withdrawal) Test
        JSONObject jo5 = new JSONObject(client.listen(new JSON(Constants.Transaction.Withdraw).add(Constants.Transaction.Amount, 500).add(Constants.Transaction.TransactionNote, "Withdrawal").toString()));
        System.out.println(jo5.toString());
        System.out.println("Withdrawal : "+jo5.get(Constants.Transaction.Withdraw));
        System.out.println("Balance : "+jo5.get(Constants.Account.GetAccountBalance));

        //Transaction (Transfer) Test
        JSONObject jo6 = new JSONObject(client.listen(new JSON(Constants.Transaction.Transfer).add(Constants.Transaction.Amount, 100).add(Constants.Transaction.TransactionNote, "Transfer").add(Constants.Transaction.Payee, "2").toString()));
        System.out.println(jo6.toString());
        System.out.println("Transferred : "+jo6.get(Constants.Transaction.Transfer));
        System.out.println("Balance : "+jo6.get(Constants.Account.GetAccountBalance));

        //Logout
        JSONObject logout = new JSONObject(client.listen(new JSON(Constants.User.Logout).toString()));

        client.close();
    }
}
