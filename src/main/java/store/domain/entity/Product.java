package store.domain.entity;

import store.domain.PromotionType;
import store.domain.vo.Name;
import store.domain.vo.Money;
import store.domain.vo.Quantity;

public class Product {
    private final Name name;
    private final Money money;
    private final Quantity quantity;
    private final PromotionType promotionType;

    private Product(Name name, Money money, Quantity quantity, PromotionType promotionType) {
        this.name = name;
        this.money = money;
        this.quantity = quantity;
        this.promotionType = promotionType;
    }

    public static Product create(Name name, Money money, Quantity quantity, PromotionType promotionType) {
        return new Product(name, money, quantity, promotionType);
    }

    public Name getName() {
        return name;
    }

    public Money getMoney() {
        return money;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }
}
