package ATM.Client;

import java.io.IOException;
import java.util.Scanner;

public class ATMClient {
    public static void main(String[] args) throws IOException {
        //Sample codes: read system.in and send to server,
        //server will return a response
        Scanner s = new Scanner(System.in);
        new Client().listen(s.nextLine());
    }
}
