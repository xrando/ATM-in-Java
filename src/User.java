import java.util.List;

public class User {
    private String Username;
    private byte[] Password;
    private List<Account> Accounts;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public byte[] getPassword() {
        return Password;
    }

    public void setPassword(byte[] password) {
        Password = password;
    }

    public List<Account> getAccounts() {
        return Accounts;
    }

    public void setAccounts(List<Account> userAccounts) {
        Accounts = userAccounts;
    }
}
