package store.domain.entity;

import store.domain.PromotionType;
import store.domain.vo.Name;
import store.domain.vo.Money;
import store.domain.vo.Quantity;

public class Product {
    private final Name name;
    private final Money money;
    private final PromotionType promotionType;
    private Quantity quantity;

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

    public int getPrice() {
        return money.getPrice();
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public boolean isStockNotAvailable(Quantity requestedQuantity) {
        return quantity.isNotEnough(requestedQuantity);
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = Quantity.newInstance(newQuantity);
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        Product product = (Product) obj;
//        return name.equals(product.name) &&
//                money.equals(product.money) &&
//                quantity.equals(product.quantity) &&
//                promotionType == product.promotionType;
//    }
}
