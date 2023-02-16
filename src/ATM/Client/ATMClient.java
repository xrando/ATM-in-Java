package ATM.Client;

import ATM.Constants.Constants;
import ATM.Utilities.ATMRequestBuilder;

import java.util.Scanner;

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

        //login
        System.out.println(client.listen(new ATMRequestBuilder(Constants.User.Login).add("test").add("123123").toString()));


        //logout
        System.out.println(client.listen(new ATMRequestBuilder(Constants.User.Logout).toString()));
        client.close();
    }
}
