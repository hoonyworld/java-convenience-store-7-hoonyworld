package store.domain.vo;

import java.util.Objects;
import store.exception.ArgumentErrorMessage;
import store.exception.StoreArgumentException;

public class Money {
    private final int price;

    private Money(int price) {
        validate(price);
        this.price = price;
    }

    public static Money newInstance(int amount) {
        return new Money(amount);
    }

    private void validate(int amount) {
        if (amount < 0) {
            throw StoreArgumentException.from(ArgumentErrorMessage.INVALID_MONEY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money money)) {
            return false;
        }
        return price == money.price;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }

    @Override
    public String toString() {
        return String.format("%,d원", price);
    }
}
