package store.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.exception.StoreArgumentException;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    @DisplayName("금액이 0 이상일 경우 Money 인스턴스를 생성한다")
    void createMoneyWithValidAmount() {
        Money money = Money.newInstance(1000);
        assertNotNull(money);
        assertEquals("1,000원", money.toString());
    }

    @Test
    @DisplayName("금액이 0 미만일 경우 예외를 발생시킨다")
    void createMoneyWithInvalidAmount() {
        StoreArgumentException exception = assertThrows(StoreArgumentException.class, () -> Money.newInstance(-100));
        assertEquals("[ERROR] 금액은 0 이상의 값이어야 합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("동일한 금액을 가진 Money 인스턴스는 equals 비교에서 같아야 한다")
    void testEqualsWithSameAmount() {
        Money money1 = Money.newInstance(500);
        Money money2 = Money.newInstance(500);
        assertEquals(money1, money2);
    }

    @Test
    @DisplayName("서로 다른 금액을 가진 Money 인스턴스는 equals 비교에서 달라야 한다")
    void testEqualsWithDifferentAmount() {
        Money money1 = Money.newInstance(500);
        Money money2 = Money.newInstance(1000);
        assertNotEquals(money1, money2);
    }

    @Test
    @DisplayName("toString 메서드는 금액을 쉼표로 구분하고 '원'을 붙여 반환한다")
    void testToString() {
        Money money = Money.newInstance(1000000);
        assertEquals("1,000,000원", money.toString());
    }
}
