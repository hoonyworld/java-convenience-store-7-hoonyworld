package store.dto;

import store.domain.entity.Product;
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
    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getMoney(),
                product.getQuantity(),
                product.getPromotionType()
        );
    }

    public Product toEntity() {
        return Product.create(name, money, quantity, promotionType);
    }
}
