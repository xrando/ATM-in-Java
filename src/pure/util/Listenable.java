package pure.util;

/**
 * Represents a function with String as return result, and take in any number of generic parameters.
 * <br><br>
 * For server/client implementation, this represents the action to be taken during the communication.
 * **/
public interface Listenable <R> {
    /**
     * @param input represents the information needed for socket communications.
     * */
    <T> R listen(T... input);
}
