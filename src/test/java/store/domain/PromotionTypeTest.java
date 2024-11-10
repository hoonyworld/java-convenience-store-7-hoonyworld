package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class PromotionTypeTest {

    @Test
    @DisplayName("유효한 설명으로 PromotionType을 생성한다.")
    void from_validDescription() {
        assertEquals(PromotionType.CARBONATED_TWO_PLUS_ONE, PromotionType.from("탄산2+1"));
        assertEquals(PromotionType.MD_RECOMMENDED, PromotionType.from("MD추천상품"));
        assertEquals(PromotionType.FLASH_SALE, PromotionType.from("반짝할인"));
    }

    @Test
    @DisplayName("유효하지 않은 설명으로 PromotionType.NONE을 반환한다.")
    void from_invalidDescription() {
        assertSame(PromotionType.NONE, PromotionType.from("유효하지 않은 설명"));
        assertSame(PromotionType.NONE, PromotionType.from(""));
        assertSame(PromotionType.NONE, PromotionType.from(null));
    }

    @Test
    @DisplayName("PromotionType의 설명을 반환하는 getDescription을 테스트한다.")
    void getDescription() {
        assertEquals("탄산2+1", PromotionType.CARBONATED_TWO_PLUS_ONE.getDescription());
        assertEquals("MD추천상품", PromotionType.MD_RECOMMENDED.getDescription());
        assertEquals("반짝할인", PromotionType.FLASH_SALE.getDescription());
        assertEquals("null", PromotionType.NONE.getDescription());
    }

    @Test
    @DisplayName("toString이 PromotionType의 설명과 일치하는지 테스트한다.")
    void toStringTest() {
        assertEquals("탄산2+1", PromotionType.CARBONATED_TWO_PLUS_ONE.toString());
        assertEquals("MD추천상품", PromotionType.MD_RECOMMENDED.toString());
        assertEquals("반짝할인", PromotionType.FLASH_SALE.toString());
        assertEquals("null", PromotionType.NONE.toString());
    }
}
