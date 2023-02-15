package ATM.Client;

import java.util.Scanner;

public class ATMClient {
    public static void main(String[] args) throws Exception {
        //Sample codes: read system.in and send to server,
        //server will return a response
        Scanner s = new Scanner(System.in);
        Client client = new Client();
        //can have multiple listen events here
        //each listen event will return a string from server
        System.out.println(client.listen(s.nextLine()));
        System.out.println(client.listen(s.nextLine()));
        client.close();
    }
}
