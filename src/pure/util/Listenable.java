package pure.util;

@FunctionalInterface
public interface Listenable {
    <T> String listen(T... input);
}
