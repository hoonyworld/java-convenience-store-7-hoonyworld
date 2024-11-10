package store.domain;

import java.util.Arrays;

public enum PromotionType {
    CARBONATED_TWO_PLUS_ONE("탄산2+1"),
    MD_RECOMMENDED("MD추천상품"),
    FLASH_SALE("반짝할인"),
    NONE("null");

    private final String description;

    PromotionType(String description) {
        this.description = description;
    }

    public static PromotionType from(String promotionDescription) {
        return Arrays.stream(PromotionType.values())
                .filter(type -> type.matchesDescription(promotionDescription))
                .findFirst()
                .orElse(NONE);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

    private boolean matchesDescription(String promotionDescription) {
        return description.equals(promotionDescription);
    }
}
