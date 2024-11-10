package store.dto;

import java.time.LocalDate;
import store.domain.PromotionType;
import store.domain.entity.Promotion;

public record PromotionDTO(
        PromotionType name,
        int buy,
        int get,
        LocalDate startDate,
        LocalDate endDate
) {
    public static PromotionDTO from(Promotion promotion) {
        return new PromotionDTO(
                promotion.getName(),
                promotion.getBuy(),
                promotion.getGet(),
                promotion.getStartDate(),
                promotion.getEndDate()
        );
    }

    public Promotion toEntity() {
        return Promotion.create(name, buy, get, startDate, endDate);
    }
}
