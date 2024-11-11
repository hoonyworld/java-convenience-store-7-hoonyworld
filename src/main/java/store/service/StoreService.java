package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import store.domain.entity.Product;
import store.domain.entity.Promotion;
import store.domain.vo.Name;
import store.dto.ProductSelectionDTO;
import store.dto.PromotionResult;

public class StoreService {
    private final ProductService productService;
    private final PromotionService promotionService;

    public StoreService(ProductService productService, PromotionService promotionService) {
        this.productService = productService;
        this.promotionService = promotionService;
    }

    public List<PromotionResult> processPurchase(List<ProductSelectionDTO> selections) {
        LocalDate currentDate = DateTimes.now().toLocalDate();
        List<PromotionResult> results = new ArrayList<>();

        for (ProductSelectionDTO selection : selections) {
            Product product = productService.findProductByNameAsEntity(selection.name());

            Set<Name> productNames = new HashSet<>();
            productService.validateUniqueProductName(selection, productNames);
            productService.validateStockAvailability(selection, product);

            List<Promotion> promotions = promotionService.findAllPromotionsAsEntities().stream()
                    .filter(promotion -> promotion.isPromotionActive(currentDate))
                    .collect(Collectors.toList());

            PromotionResult result = applyPromotionIfEligible(selection.quantity().getAmount(), product, promotions);
            results.add(result);

        }

        return results;
    }

    public void applyMembershipDiscount(List<PromotionResult> results) {
        for (PromotionResult result : results) {
            if (!result.hasPromotion()) {
                result.applyMembershipDiscount();
            }
        }
    }

    public PromotionResult applyPromotionIfEligible(int requestedQuantity, Product product, List<Promotion> promotions) {
        boolean promotionAvailable = false;
        int promotionalQuantity = 0;
        int nonPromotionQuantity = requestedQuantity;
        int pricePerUnit = product.getPrice();
        int totalPrice = requestedQuantity * pricePerUnit;
        int discount = 0;

        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(product.getPromotionType())) {
                promotionAvailable = true;
                int buyRequirement = promotion.getBuy();
                int getFreeQuantity = promotion.getGet();

                promotionalQuantity = requestedQuantity / (buyRequirement + getFreeQuantity);
                nonPromotionQuantity = requestedQuantity - promotionalQuantity;

                discount = promotionalQuantity * pricePerUnit;
                break;
            }
        }

        boolean hasSufficientStock = product.getQuantity().getAmount() >= promotionalQuantity;
        boolean canReceiveMorePromotion = canReceiveMorePromotion(requestedQuantity, promotions, product);

        return new PromotionResult(
                product.getName().toString(),
                requestedQuantity,
                promotionalQuantity,
                nonPromotionQuantity,
                promotionAvailable,
                hasSufficientStock,
                totalPrice,
                discount,
                pricePerUnit,
                0,
                canReceiveMorePromotion,
                product
        );
    }

    private boolean canReceiveMorePromotion(int requestedQuantity, List<Promotion> promotions, Product product) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(product.getPromotionType())) {
                int buyRequirement = promotion.getBuy();
                int getFreeQuantity = promotion.getGet();

                if (isTwoPlusOnePromotion(buyRequirement, getFreeQuantity)) {
                    return needsMorePromotionForTwoPlusOne(requestedQuantity, buyRequirement, getFreeQuantity);
                }
                if (isOnePlusOnePromotion(buyRequirement, getFreeQuantity)) {
                    return needsMorePromotionForOnePlusOne(requestedQuantity, buyRequirement);
                }
                return false;
            }
        }
        return false;
    }

    private boolean isTwoPlusOnePromotion(int buyRequirement, int getFreeQuantity) {
        return buyRequirement == 2 && getFreeQuantity == 1;
    }

    private boolean isOnePlusOnePromotion(int buyRequirement, int getFreeQuantity) {
        return buyRequirement == 1 && getFreeQuantity == 1;
    }

    private boolean needsMorePromotionForTwoPlusOne(int requestedQuantity, int buyRequirement, int getFreeQuantity) {
        int promotionUnit = buyRequirement + getFreeQuantity;
        return (requestedQuantity % promotionUnit != 0) && (requestedQuantity % promotionUnit != 1);
    }

    private boolean needsMorePromotionForOnePlusOne(int requestedQuantity, int buyRequirement) {
        return requestedQuantity % (buyRequirement + 1) != 0;
    }
}
