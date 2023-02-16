package ATM.Client;

import ATM.Constants.Constants;
import ATM.Utilities.JSON;

import ATM.Bank.User;

public class ATMClient {
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        //can have multiple listen events here
        //each listen event will return a string from server
/*        while (true){
            String returnMsg = null;
            if (returnMsg.equals(Constants.Stream.EOS))
                break;

        }*/

        //These codes are for testing, actual implementation will be with UI

        User user = new User();

        // Create a new user
        //user.CreateUser();

        System.out.print(client.listen(new JSON(Constants.User.CreateUser).add(Constants.User.Username, "abc").add(Constants.User.Password, "123123").toString()));

        //login
        //System.out.print(client.listen(new JSON(Constants.User.Login).add(Constants.User.Username, "test").add(Constants.User.Password, "123123").toString()));
        //System.out.println(client.listen(new ATMRequestBuilder(Constants.User.Login).add("test").add("123123").toString()));



        //System.out.print(client.listen(new JSON(Constants.User.ChangePin).add(Constants.User.oldPin, "123456").add(Constants.User.newPin, "123456").toString()));


        //System.out.print(client.listen(new JSON(Constants.User.Logout).toString()));
        //logout
        //System.out.println(client.listen(new ATMRequestBuilder(Constants.User.Logout).toString()));
        client.close();
    }
}
