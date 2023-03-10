package ATM.Utilities;

public interface ATMSockets extends AutoCloseable {
    String read();
    void write(String s);
    void close();
}
