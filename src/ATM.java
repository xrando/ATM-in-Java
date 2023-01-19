import java.util.Scanner;

public class ATM
{
    public static void main(String[] args)
    {
        //Initialize scanner
        Scanner scanner = new Scanner(System.in);

        //Initialize bank
        Bank NewBank = new Bank("Test Bank");

        //Add user, which also creates a savings account
        User User1 = NewBank.addBankUser("Ben","1234","Test1234@gmail.com","98765432");

        //Add a checking account for user1
        Account NewAccount = new Account("Checking",User1,NewBank);
        User1.AddAccount(NewAccount);
        NewBank.AddAccount(NewAccount);
    }
}
