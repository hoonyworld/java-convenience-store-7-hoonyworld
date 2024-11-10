package store.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.exception.StoreArgumentException;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    @DisplayName("정상적인 이름으로 Name 인스턴스를 생성한다.")
    void createName_success() {
        Name name = Name.newInstance("ValidName");
        assertEquals("ValidName", name.toString());
    }

    @Test
    @DisplayName("빈 문자열로 Name 인스턴스를 생성할 때 예외를 발생시킨다.")
    void createName_emptyString() {
        Exception exception = assertThrows(StoreArgumentException.class, () -> Name.newInstance(""));
        assertEquals("[ERROR] 이름은 null이거나 빈 값일 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("null로 Name 인스턴스를 생성할 때 예외를 발생시킨다.")
    void createName_nullValue() {
        Exception exception = assertThrows(StoreArgumentException.class, () -> Name.newInstance(null));
        assertEquals("[ERROR] 이름은 null이거나 빈 값일 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("동일한 값을 가진 Name 객체는 equals 메서드로 동일성을 검증한다.")
    void equals_sameValue() {
        Name name1 = Name.newInstance("SameName");
        Name name2 = Name.newInstance("SameName");
        assertEquals(name1, name2);
    }

    @Test
    @DisplayName("다른 값을 가진 Name 객체는 equals 메서드로 다름을 검증한다.")
    void equals_differentValue() {
        Name name1 = Name.newInstance("Name1");
        Name name2 = Name.newInstance("Name2");
        assertNotEquals(name1, name2);
    }

    @Test
    @DisplayName("동일한 이름 값으로 생성된 hashCode는 동일하게 생성된다.")
    void hashCode_sameValue() {
        Name name1 = Name.newInstance("SameName");
        Name name2 = Name.newInstance("SameName");
        assertEquals(name1.hashCode(), name2.hashCode());
    }

    @Test
    @DisplayName("다른 이름 값으로 생성된 hashCode는 다른 값을 생성한다.")
    void hashCode_differentValue() {
        Name name1 = Name.newInstance("Name1");
        Name name2 = Name.newInstance("Name2");
        assertNotEquals(name1.hashCode(), name2.hashCode());
    }
}
