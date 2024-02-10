package site.jigume.domain.goods.entity;

public enum DepositPolicy {
    GOODS_PRICE_THRESHOLD(50000),
    RATE(10),
    DEFAULT_DEPOSIT(3500);

    private int value;

    DepositPolicy(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
