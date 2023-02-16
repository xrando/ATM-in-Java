import ATM.Bank.Account;
import ATM.Bank.Bank;
import ATM.Bank.User;

import java.util.Scanner;

public class ATM
{
    public static void main(String[] args)
    {
        //Initialize scanner
        Scanner scanner = new Scanner(System.in);

        //Initialize bank
        Bank BankObj = new Bank("Test ATM.Bank.Bank");

        //Add user, which also creates a savings account
        User NewUser = BankObj.addBankUser("Ben","1234","Test1234@gmail.com","98765432");

        //Add a checking account for user1
        Account NewAccount = new Account("Checking",NewUser,BankObj);
        NewUser.AddAccount(NewAccount);
        BankObj.AddAccount(NewAccount);

        //Current ATM.ATM.Bank.Bank.User
        User CurrentUser;
        while(true)
        {
            //Stay in the login prompt until successful login
            CurrentUser = ATM.MainMenuPrompt(BankObj, scanner);

            //Stay in main menu until user quits
            ATM.PrintUserMenu(CurrentUser, scanner);
        }
    }
    //Login menu
    public static User MainMenuPrompt(Bank BankObj, Scanner scanner)
    {
        //Initialize
        String UID;
        String password;
        User AuthorizedUser;
        do
        {
            System.out.printf("\n\nWelcome to %s\n\n", BankObj.getBankName());
            System.out.print("Enter UID: ");
            UID = scanner.nextLine();
            System.out.print("Enter password: ");
            password = scanner.nextLine();

            //Get user object corresponding to UID and password combo
            AuthorizedUser = BankObj.UserLogin(UID,password);
            if (AuthorizedUser == null)
            {
                System.out.println("Incorrect UID/password combination. Please try again");
            }
        }
        //continue looping till ATM.ATM.Bank.Bank.User login successfully
        while (AuthorizedUser==null);
        return AuthorizedUser;
    }
    //UI
    public static void PrintUserMenu(User NewUser, Scanner scanner)
    {
        //Print all user's account summary
        NewUser.PrintAllAccountSummary();

        //Initialize
        int choice;

        //ATM.ATM.Bank.Bank.User menu
        do
        {
            System.out.printf("Welcome %s, what would you like to do?\n",NewUser.getUsername());
            System.out.println("1)Show transaction history");
            System.out.println("2)Withdraw Money");
            System.out.println("3)Deposit Money");
            System.out.println("4)Transfer Money");
            System.out.println("5)ATM.ATM.Bank.Bank.Account Settings");
            System.out.println("6)Quit\n");
            System.out.print("Select Option: ");
            choice = scanner.nextInt();
            if (choice < 1 || choice > 5)
            {
                System.out.println("Invalid choice chosen. Please choose options 1 to 5");
            }
        }
        while (choice < 1 || choice > 5);
        //Execute Choice
        switch (choice)
        {
            case 1:
                ATM.ShowTransaction(NewUser,scanner);
                break;
            case 2:
                ATM.WithdrawMoney(NewUser,scanner);
                break;
            case 3:
                ATM.DepositMoney(NewUser,scanner);
                break;
            case 4:
                ATM.TransferMoney(NewUser,scanner);
                break;
            case 5:
                //ATM.ATM.Bank.Bank.Account settings, change password/name etc
                ATM.UserSettings(NewUser,scanner);
                break;
            case 6:
                //Gobble previous input
                scanner.nextLine();
                break;
        }

        //Display UI
        if (choice !=5)
        {
            ATM.PrintUserMenu(NewUser, scanner);
        }
    }
    //Show transaction history for an account
    public static void ShowTransaction(User NewUser, Scanner scanner)
    {
        int UserAccount;
        do
        {
            System.out.printf("Choose the account number whose transactions you want to view (1 to %d): ", NewUser.getAccounts().size());
            UserAccount = scanner.nextInt()-1;
            if (UserAccount < 0 || UserAccount >= NewUser.getAccounts().size())
            {
                System.out.println("Invalid account chosen, please try again.");
            }
        }
        while (UserAccount < 0 || UserAccount >= NewUser.getAccounts().size());

        //Print transaction history
        NewUser.PrintAccountTransactionHistory(UserAccount);
    }
    //Transfer money from an account to another account
    public static void TransferMoney(User NewUser, Scanner scanner)
    {
        //Initialize
        int TransferFrom,TransferTo;
        double Amount,AccountBalance;

        //Get account to transfer from
        do
        {
            System.out.printf("Choose the account number to transfer from (1 to %d): ", NewUser.getAccounts().size());
            TransferFrom = scanner.nextInt()-1;
            if (TransferFrom < 0 || TransferFrom >= NewUser.getAccounts().size())
            {
                System.out.println("Invalid account chosen, please try again.");
            }
        } while (TransferFrom < 0 || TransferFrom >= NewUser.getAccounts().size());
        AccountBalance = NewUser.GetAccountBalance(TransferFrom);

        //Get account to transfer to
        do
        {
            System.out.printf("Choose the account number to transfer to (1 to %d): ", NewUser.getAccounts().size());
            TransferTo = scanner.nextInt()-1;
            if (TransferTo < 0 || TransferTo >= NewUser.getAccounts().size())
            {
                System.out.println("Invalid account chosen, please try again.");
            }
        } while (TransferTo < 0 || TransferTo >= NewUser.getAccounts().size());
        //Get amount to transfer
        do
        {
            System.out.printf("Enter the amount to transfer (ATM.ATM.Bank.Bank.Account Balance: $%.2f): $",AccountBalance);
            Amount = scanner.nextDouble();
            if (Amount<0)
            {
                System.out.println("Amount can not be below zero. Please enter a valid amount.");
            }
            else if (Amount>AccountBalance)
            {
                System.out.printf("There is insufficient money in this account (ATM.ATM.Bank.Bank.Account Balance: $%.2f). Please enter a valid amount.",AccountBalance);
            }
        } while (Amount<0 || Amount>AccountBalance);

        //Transfer money, format TransactionNote
        NewUser.AddAccountTransaction(TransferFrom,-1*Amount, String.format("Transfer to account %s", NewUser.GetAccountUID(TransferTo)));
        NewUser.AddAccountTransaction(TransferTo,Amount, String.format("Transfer to account %s", NewUser.GetAccountUID(TransferFrom)));
    }
    //Withdraw money from an account
    public static void WithdrawMoney(User NewUser, Scanner scanner)
    {
        //Initialize
        int WithdrawFrom;
        double Amount,AccountBalance;
        String TransactionNote;

        //Get account to withdraw from
        do
        {
            System.out.printf("Choose the account number to withdraw from (1 to %d): ", NewUser.getAccounts().size());
            WithdrawFrom = scanner.nextInt()-1;
            if (WithdrawFrom < 0 || WithdrawFrom >= NewUser.getAccounts().size())
            {
                System.out.println("Invalid account chosen, please try again.");
            }
        } while (WithdrawFrom < 0 || WithdrawFrom >= NewUser.getAccounts().size());
        AccountBalance = NewUser.GetAccountBalance(WithdrawFrom);

        //Get amount to withdraw
        do
        {
            System.out.printf("Enter the amount to withdraw (ATM.ATM.Bank.Bank.Account Balance: $%.2f): $",AccountBalance);
            Amount = scanner.nextDouble();
            if (Amount<0)
            {
                System.out.println("Amount can not be below zero. Please enter a valid amount.");
            }
            else if (Amount>AccountBalance)
            {
                System.out.printf("There is insufficient money in this account (ATM.ATM.Bank.Bank.Account Balance: $%.2f). Please enter a valid amount.",AccountBalance);
            }
            else if (Amount>50000)
            {
                //Notify incoming huge withdrawal via sms/email

            }
        } while (Amount<0 || Amount>AccountBalance);

        //Gobble previous input
        scanner.nextLine();

        //Get transaction note
        System.out.print("Enter a transaction note: ");
        TransactionNote = scanner.nextLine();

        //Withdraw money
        NewUser.AddAccountTransaction(WithdrawFrom,-1*Amount, TransactionNote);
    }
    //Deposit money to an account
    public static void DepositMoney(User NewUser, Scanner scanner)
    {
        //Initialize
        int DepositTo;
        double Amount,AccountBalance;
        String TransactionNote;

        //Get account to deposit to
        do
        {
            System.out.printf("Choose the account number to deposit to (1 to %d): ", NewUser.getAccounts().size());
            DepositTo = scanner.nextInt()-1;
            if (DepositTo < 0 || DepositTo >= NewUser.getAccounts().size())
            {
                System.out.println("Invalid account chosen, please try again.");
            }
        } while (DepositTo < 0 || DepositTo >= NewUser.getAccounts().size());
        AccountBalance = NewUser.GetAccountBalance(DepositTo);

        //Get amount to deposit
        do
        {
            System.out.println("Enter the amount to deposit: $");
            Amount = scanner.nextDouble();
            if (Amount<0)
            {
                System.out.println("Amount can not be below zero. Please enter a valid amount.");
            }
            else if (Amount>50000)
            {
                //Notify incoming huge deposit via sms/email

            }
        } while (Amount<0);
        //Gobble previous input
        scanner.nextLine();

        //Get transaction note
        System.out.print("Enter a transaction note: ");
        TransactionNote = scanner.nextLine();

        //Withdraw money
        NewUser.AddAccountTransaction(DepositTo, Amount, TransactionNote);
    }
    //ATM.ATM.Bank.Bank.User settings
    public static void UserSettings(User NewUser, Scanner scanner)
    {
        //reset password

        //change username

        //change number

        //change email
    }
}
