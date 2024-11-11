package store.controller;

import java.util.List;
import store.domain.entity.Promotion;
import store.dto.ProductSelectionDTO;
import store.dto.PromotionResult;
import store.exception.StoreArgumentException;
import store.service.PromotionService;
import store.service.StoreService;
import store.template.RetryTemplate;
import store.view.InputView;
import store.view.OutputView;

public class PurchaseController {
    private final StoreService storeService;
    private final PromotionService promotionService;
    private final InputView inputView;
    private final OutputView outputView;
    private final RetryTemplate retryTemplate;
    private final ProductController productController;

    public PurchaseController(StoreService storeService, PromotionService promotionService, InputView inputView,
                              OutputView outputView, RetryTemplate retryTemplate, ProductController productController) {
        this.storeService = storeService;
        this.promotionService = promotionService;
        this.inputView = inputView;
        this.outputView = outputView;
        this.retryTemplate = retryTemplate;
        this.productController = productController;
    }

    public void handlePurchaseWithRetryDisplay() {
        List<ProductSelectionDTO> selections = getSelectionsWithRetry();
        List<PromotionResult> results = getPurchaseResultsWithRetry(selections);

        for (int i = 0; i < results.size(); i++) {
            PromotionResult result = results.get(i);

            if (shouldProcessPromotion(result)) {
                boolean userConfirmed = getUserConfirmationForPromotion(result);

                if (userConfirmed && result.isHasSufficientStock()) {
                    processPromotion(result, results, i);
                } else {
                    result.removeNonPromotionalItems();
                    results.set(i, result); // Update with removed items
                }
            }
        }

        boolean applyMembership = getMembershipConfirmation();
        if (applyMembership) {
            storeService.applyMembershipDiscount(results);
        }

        outputView.displayPurchaseResult(results);
    }

    // 구매 결과 처리 시 재입력 메소드
    private List<PromotionResult> getPurchaseResultsWithRetry(List<ProductSelectionDTO> selections) {
        return retryTemplate.execute(() -> {
            try {
                return storeService.processPurchase(selections);  // 예외 발생 시 재입력
            } catch (StoreArgumentException e) {
                outputView.printExceptionMessage(e);
                productController.displayProducts();
                // 상품 선택을 다시 받기
                List<ProductSelectionDTO> newSelections = getSelectionsWithRetry();
                return storeService.processPurchase(newSelections);  // 재입력 후 다시 처리
            }
        });
    }

    private List<ProductSelectionDTO> getSelectionsWithRetry() {
        return retryTemplate.execute(() -> {
            try {
                return inputView.readProductSelections();
            } catch (StoreArgumentException e) {
                outputView.printExceptionMessage(e);
                productController.displayProducts(); // Re-display product list on invalid input
                throw e;
            }
        });
    }

    private boolean shouldProcessPromotion(PromotionResult result) {
        return result.requiresPromotionQuantityMessage() && result.isCanReceiveMorePromotion();
    }

    private boolean getUserConfirmationForPromotion(PromotionResult result) {
        final String productName = result.getProductName();
        return retryTemplate.execute(() -> {
            try {
                outputView.displayPromotionQuantityMessage(productName);
                return inputView.readUserConfirmation();
            } catch (StoreArgumentException e) {
                outputView.printExceptionMessage(e);
                throw e;
            }
        });
    }

    private void processPromotion(PromotionResult result, List<PromotionResult> results, int index) {
        int requestedQuantity = result.getTotalQuantity() + 1;

        if (isStockSufficient(result, requestedQuantity)) {
            List<Promotion> promotions = promotionService.findAllPromotionsAsEntities();
            results.set(index, storeService.applyPromotionIfEligible(requestedQuantity, result.getProduct(), promotions));
        } else if (!handlePurchaseWithoutPromotion(result.getProductName(), result.getTotalQuantity())) {
            // Continue to next result if purchase without promotion is declined
            results.set(index, result);
        }
    }

    private boolean isStockSufficient(PromotionResult result, int requestedQuantity) {
        if (result.getProduct().getQuantity().getAmount() < requestedQuantity) {
            outputView.displayStockInsufficientMessage(result.getProductName());
            outputView.displayNonPromotionalPurchaseConfirmation(result.getProductName(), result.getTotalQuantity());
            return false;
        }
        return true;
    }

    private boolean handlePurchaseWithoutPromotion(String productName, int totalQuantity) {
        return retryTemplate.execute(() -> {
            try {
                return inputView.readUserConfirmation();
            } catch (StoreArgumentException e) {
                outputView.printExceptionMessage(e);
                outputView.displayStockInsufficientMessage(productName);
                outputView.displayNonPromotionalPurchaseConfirmation(productName, totalQuantity);
                throw e;
            }
        });
    }

    private boolean getMembershipConfirmation() {
        outputView.displayMembershipPrompt();
        return retryTemplate.execute(() -> {
            try {
                return inputView.readUserConfirmation();
            } catch (StoreArgumentException e) {
                outputView.printExceptionMessage(e);
                outputView.displayMembershipPrompt();
                throw e;
            }
        });
    }
}
