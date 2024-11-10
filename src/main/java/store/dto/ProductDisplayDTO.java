package store.dto;

import store.domain.vo.Name;
import store.domain.PromotionType;
import store.domain.vo.Money;
import store.domain.vo.Quantity;

public record ProductDisplayDTO(
        Name name,
        Money money,
        Quantity quantity,
        PromotionType promotionType
) {
    public static ProductDisplayDTO of(Name name, Money money, Quantity quantity, PromotionType promotionType) {
        return new ProductDisplayDTO(name, money, quantity, promotionType);
    }
}
