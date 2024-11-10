package store.domain.vo;

import java.util.Objects;
import store.exception.ArgumentErrorMessage;
import store.exception.StoreArgumentException;

public class Name {
    private final String value;

    private Name(String value) {
        validate(value);
        this.value = value;
    }

    public static Name newInstance(String value) {
        return new Name(value);
    }

    private void validate(String value) {
        if (value == null || value.isEmpty()) {
            throw StoreArgumentException.from(ArgumentErrorMessage.INVALID_NAME);
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Name name)) {
            return false;
        }
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
