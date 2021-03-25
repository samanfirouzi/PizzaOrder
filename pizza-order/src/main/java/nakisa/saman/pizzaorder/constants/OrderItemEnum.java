package nakisa.saman.pizzaorder.constants;

public enum OrderItemEnum {
    PRODUCT(1),
    PROMOTION(2);

    private final Integer value;

    OrderItemEnum(final Integer newValue) {
        value = newValue;
    }

    public Integer getValue() { return value; }
}
