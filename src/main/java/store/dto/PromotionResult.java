package store.dto;

import store.domain.entity.Product;

public class PromotionResult {
    private final String productName;
    private final int promotionalQuantity;
    private final boolean promotionAvailable;
    private final boolean hasSufficientStock;
    private final int pricePerUnit;
    private final int discount;
    private final Product product;

    private int totalQuantity;
    private int nonPromotionQuantity;
    private int totalPrice;
    private int membershipDiscount;
    private boolean canReceiveMorePromotion;

    public PromotionResult(String productName, int totalQuantity, int promotionalQuantity, int nonPromotionQuantity,
                           boolean promotionAvailable, boolean hasSufficientStock, int totalPrice, int discount,
                           int pricePerUnit, int membershipDiscount, boolean canReceiveMorePromotion, Product product) {
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.promotionalQuantity = promotionalQuantity;
        this.nonPromotionQuantity = nonPromotionQuantity;
        this.promotionAvailable = promotionAvailable;
        this.hasSufficientStock = hasSufficientStock;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.pricePerUnit = pricePerUnit;
        this.membershipDiscount = membershipDiscount;
        this.canReceiveMorePromotion = canReceiveMorePromotion;
        this.product = product;
    }

    public boolean requiresPromotionQuantityMessage() {
        return promotionAvailable && nonPromotionQuantity > 0 && hasSufficientStock;
    }

    public String getProductName() {
        return productName;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getPromotionalQuantity() {
        return promotionalQuantity;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public int getDiscount() {
        return discount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public boolean isCanReceiveMorePromotion() {
        return canReceiveMorePromotion;
    }

    public boolean isHasSufficientStock() {
        return hasSufficientStock;
    }

    public Product getProduct() {
        return product;
    }

    public void applyMembershipDiscount() {
        if (!promotionAvailable) {
            membershipDiscount = (int) (totalPrice * 0.3);
            totalPrice -= membershipDiscount;
        }
    }

    public void removeNonPromotionalItems() {
        if (nonPromotionQuantity > 0) {
            totalPrice -= nonPromotionQuantity * pricePerUnit;
            totalQuantity -= nonPromotionQuantity;
            nonPromotionQuantity = 0;
        }
    }

    public boolean hasPromotion() {
        return promotionAvailable && promotionalQuantity > 0;
    }
}
