package store.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    @Test
    @DisplayName("정상적인 양수 값으로 Quantity 인스턴스를 생성한다.")
    void createQuantity_success() {
        Quantity quantity = Quantity.newInstance(5);
        assertEquals("5개", quantity.toString());
    }

    @Test
    @DisplayName("0으로 Quantity 인스턴스를 생성하여 isZero가 true임을 확인한다.")
    void createQuantity_zero() {
        Quantity quantity = Quantity.newInstance(0);
        assertTrue(quantity.isZero());
    }

    @Test
    @DisplayName("음수 값으로 Quantity 인스턴스 생성 시 예외가 발생한다.")
    void createQuantity_negativeValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Quantity.newInstance(-1));
        assertEquals("[ERROR] 수량은 0보다 크거나 같아야 합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("동일한 수량 값을 가진 Quantity 객체는 equals 메서드로 동일성을 검증한다.")
    void equals_sameValue() {
        Quantity quantity1 = Quantity.newInstance(5);
        Quantity quantity2 = Quantity.newInstance(5);
        assertEquals(quantity1, quantity2);
    }

    @Test
    @DisplayName("다른 수량 값을 가진 Quantity 객체는 equals 메서드로 다름을 검증한다.")
    void equals_differentValue() {
        Quantity quantity1 = Quantity.newInstance(5);
        Quantity quantity2 = Quantity.newInstance(10);
        assertNotEquals(quantity1, quantity2);
    }

    @Test
    @DisplayName("hashCode는 동일한 수량 값으로 동일하게 생성된다.")
    void hashCode_sameValue() {
        Quantity quantity1 = Quantity.newInstance(5);
        Quantity quantity2 = Quantity.newInstance(5);
        assertEquals(quantity1.hashCode(), quantity2.hashCode());
    }

    @Test
    @DisplayName("hashCode는 다른 수량 값으로 다른 값이 생성된다.")
    void hashCode_differentValue() {
        Quantity quantity1 = Quantity.newInstance(5);
        Quantity quantity2 = Quantity.newInstance(10);
        assertNotEquals(quantity1.hashCode(), quantity2.hashCode());
    }
}
