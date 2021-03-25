package nakisa.saman.pizzaorder.constants;

public enum ProductEnum {
    PIZZA(1),
    DRINK(2),
    SIDE(3);


    private final int value;

    ProductEnum(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
