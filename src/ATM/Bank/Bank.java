package ATM.Bank;

import ATM.Bank.Account;
import ATM.Bank.User;

import java.util.ArrayList;
import java.util.Random;

public class Bank
{
    private String BankName;
    private ArrayList<User> BankUsers;
    private ArrayList<Account> BankAccounts;
    //Create a new ATM.Bank.Bank object with empty bank users and bank accounts
    public Bank(String NewBankName)
    {
        this.BankName = NewBankName;
        this.BankUsers = new ArrayList<User>();
        this.BankAccounts = new ArrayList<Account>();
    }
    public String generateNewUserUID()
    {
        Random rand = new Random();
        String UID="";
        boolean IsUnique=false;
        while (!IsUnique)
        {
            //Generate new random 6 digit ATM.ATM.Bank.Bank.User UID
            for (int i=0;i<6;i++)
            {
                UID += ((Integer)rand.nextInt(10)).toString();
            }
            //Set IsUnique flag to true
            IsUnique=true;
            //Check if new ATM.ATM.Bank.Bank.User UID is unique
            for (User user : this.BankUsers)
            {
                //If ATM.ATM.Bank.Bank.User UID already exists
                if (UID.compareTo(user.getUID()) == 0)
                {
                    //Set IsUnique flag to false
                    IsUnique = false;
                    break;
                }
            }
        }
        return UID;
    }
    public String generateNewAccountUID()
    {
        Random rand = new Random();
        String UID="";
        boolean IsUnique=false;
        while (!IsUnique)
        {
            //Generate new random 10 digit ATM.ATM.Bank.Bank.Account UID
            for (int i=0;i<10;i++)
            {
                UID += ((Integer)rand.nextInt(10)).toString();
            }
            //Set IsUnique flag to true
            IsUnique=true;
            //Check if new ATM.ATM.Bank.Bank.Account UID is unique
            for (Account account : this.BankAccounts)
            {
                //If ATM.ATM.Bank.Bank.Account UID already exists
                if (UID.compareTo(account.getUID()) == 0)
                {
                    //Set IsUnique flag to false
                    IsUnique = false;
                    break;
                }
            }
        }
        return UID;
    }
    //Add an account to bank accounts
    public void AddAccount(Account NewAccount)
    {
        this.BankAccounts.add(NewAccount);
    }
    public User addBankUser(String Username, String password, String Useremail,String Userphone)
    {
        //Create a new ATM.ATM.Bank.Bank.User object and add to list
        User NewUser = new User(Username,password,Useremail,Userphone,this);
        this.BankUsers.add(NewUser);

        //Create a savings account for the user
        Account NewAccount = new Account("Savings",NewUser,this);
        NewUser.AddAccount(NewAccount);
        this.AddAccount(NewAccount);
        return NewUser;
    }
    //Get ATM.ATM.Bank.Bank.User object associated with a particular UserId and password, login if valid
    public User UserLogin(String UserId, String password)
    {
        //Search through list of Bankusers
        for (User user : this.BankUsers)
        {
            //Check if UserId is correct
            if (user.getUID().compareTo(UserId)==0 && user.validatePassword(password, user.getUID()))
            {
                return user;
            }
        }
        //Return null if user not found or credentials are incorrect
        return null;
    }

    public String getBankName()
    {
        return BankName;
    }

    public void setBankName(String bankName)
    {
        BankName = bankName;
    }
}
