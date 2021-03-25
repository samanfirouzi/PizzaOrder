package nakisa.saman.pizzaorder.exceprions;

public class EmptyResultException extends Exception {
    public EmptyResultException(String s) {
        super("There is no " + s + " data for your request!");

    }
}
