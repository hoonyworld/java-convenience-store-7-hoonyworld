package store.dto;

import store.domain.vo.Name;
import store.domain.PromotionType;
import store.domain.vo.Money;
import store.domain.vo.Quantity;

public record ProductDTO(
        Name name,
        Money money,
        Quantity quantity,
        PromotionType promotionType
) {
    public static ProductDTO of(Name name, Money money, Quantity quantity, PromotionType promotionType) {
        return new ProductDTO(name, money, quantity, promotionType);
    }
}
