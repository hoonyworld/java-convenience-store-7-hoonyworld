package store.domain.vo;

import java.util.Objects;
import store.exception.ArgumentErrorMessage;
import store.exception.StoreArgumentException;

public class Quantity {
    private final int amount;

    private Quantity(int amount) {
        validate(amount);
        this.amount = amount;
    }

    public static Quantity newInstance(int amount) {
        return new Quantity(amount);
    }

    public boolean isZero() {
        return amount == 0;
    }

    public boolean isNotEnough(Quantity required) {
        return this.amount < required.amount;
    }

    public int getAmount() {
        return amount;
    }

    private void validate(int amount) {
        if (amount < 0) {
            throw StoreArgumentException.from(ArgumentErrorMessage.INVALID_QUANTITY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quantity quantity)) {
            return false;
        }
        return amount == quantity.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return amount + "개";
    }
}
