package nakisa.saman.pizzaorder.constants;

public enum OrderEnum {
    ACTIVE_ORDER(1),
    SAVED_FOR_LATER(2),
    LUNCHED(3),
    FAVORITE(4);

    private final int value;

    OrderEnum(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
