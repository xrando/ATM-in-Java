package pure.util;

import pure.bank.Account;
import pure.bank.Transaction;
import pure.constants.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.logging.Level;

/**
 * A wrapper class for {@link JSONObject}.
 * */
public class JSON {
    private final JSONObject jsonObject = new JSONObject();

    public <T> JSON(T type) {
        this.jsonObject.put(Constants.JSON.Type, type);
    }

    /**
     * Try to parse the string to JSONObject format.
     * <br>
     * returns null if failed.
     * */
    public static JSONObject tryParse(String input) {
        JSONObject jo = null;
        try {
            jo = new JSONObject(input);
        } catch (JSONException e) {
            LogHelper.log(Level.SEVERE, "Failed to parse string [" + input + "] to JSON.", e);
        }
        return jo;
    }

    public static String parseTransactionsToString(ArrayList<Transaction> transactions) {
        return parseTransactions(transactions).toString();
    }

    private static JSONArray parseTransactions(ArrayList<Transaction> transactions) {
        JSONArray ja = new JSONArray();
        transactions.forEach(transaction -> {
            HashMap<String, String> map = new HashMap<>();
            map.put(Constants.Transaction.date, transaction.getTransactionDate());
            map.put(Constants.Transaction.TimeStamp, transaction.getTransactionTime());
            map.put(Constants.Transaction.TransactionNote, transaction.getTransactionNote());
            map.put(Constants.Transaction.Amount, String.valueOf(transaction.getAmount()));
            ja.put(map);
        });

        // convert JSONArray to List
        List<Object> list = Arrays.asList(ja.toList().toArray());

        // reverse the List
        Collections.reverse(list);

        // convert List back to JSONArray

        return new JSONArray(list);
    }

    public static String parseAccountsToString(List<Account> accounts) {
        return parseAccounts(accounts).toString();
    }

    private static JSONArray parseAccounts(List<Account> accounts) {
        JSONArray ja = new JSONArray();
        accounts.forEach(account -> {
            HashMap<String, String> map = new HashMap<>();
            map.put(Constants.Account.AccountId, account.getAccountID());
            map.put(Constants.Account.AccountType, account.getAccountType());
            map.put(Constants.Account.UserID, account.getUID());
            ja.put(map);
        });
        return ja;
    }

    public static String parseAccountsSummaryToString(List<Account> accounts) {
        return parseAccountsSummary(accounts).toString();
    }

    private static JSONArray parseAccountsSummary(List<Account> accounts) {
        JSONArray ja = new JSONArray();
        accounts.forEach(account -> {
            HashMap<String, String> map = new HashMap<>();
            map.put(Constants.Account.AccountId, account.getAccountID());
            map.put(Constants.Account.AccountType, account.getAccountType());
            map.put(Constants.Account.Balance, String.valueOf((account.GetAccountBalance())));
            ja.put(map);
        });
        return ja;
    }

    public <T> JSON add(String key, T t) {
        try {
            this.jsonObject.put(key, t);
        } catch (JSONException e) {
            LogHelper.log(Level.SEVERE, "Error when adding JSON string.", e);
        }
        return this;
    }

    public String toString() {
        return this.jsonObject.toString();
    }
}
