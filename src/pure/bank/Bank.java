package pure.bank;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String BankName;
    private final ArrayList<User> BankUsers;
    private final ArrayList<Account> BankAccounts;

    //Create a new ATM.Bank.Bank object with empty bank users and bank accounts
    public Bank(String NewBankName) {
        this.BankName = NewBankName;
        this.BankUsers = new ArrayList<User>();
        this.BankAccounts = new ArrayList<Account>();
    }

    public String generateNewUserUID() {
        Random rand = new Random();
        String UID = "";
        boolean IsUnique = false;
        while (!IsUnique) {
            //Generate new random 6 digit ATM.ATM.Bank.Bank.User UID
            for (int i = 0; i < 6; i++) {
                UID += ((Integer) rand.nextInt(10)).toString();
            }
            //Set IsUnique flag to true
            IsUnique = true;
            //Check if new ATM.ATM.Bank.Bank.User UID is unique
            for (User user : this.BankUsers) {
                //If ATM.ATM.Bank.Bank.User UID already exists
                if (UID.compareTo(user.getUID()) == 0) {
                    //Set IsUnique flag to false
                    IsUnique = false;
                    break;
                }
            }
        }
        return UID;
    }

    public String generateNewAccountUID() {
        Random rand = new Random();
        String UID = "";
        boolean IsUnique = false;
        while (!IsUnique) {
            //Generate new random 10 digit ATM.ATM.Bank.Bank.Account UID
            for (int i = 0; i < 10; i++) {
                UID += ((Integer) rand.nextInt(10)).toString();
            }
            //Set IsUnique flag to true
            IsUnique = true;
            //Check if new ATM.ATM.Bank.Bank.Account UID is unique
            for (Account account : this.BankAccounts) {
                //If ATM.ATM.Bank.Bank.Account UID already exists
                if (UID.compareTo(account.getUID()) == 0) {
                    //Set IsUnique flag to false
                    IsUnique = false;
                    break;
                }
            }
        }
        return UID;
    }
}
