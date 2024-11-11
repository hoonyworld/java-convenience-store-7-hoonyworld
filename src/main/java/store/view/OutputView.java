package store.view;

import java.util.List;
import store.dto.ProductDisplayDTO;
import store.dto.PromotionResult;

public interface OutputView {
    void printExceptionMessage(Exception e);

    void displayProductCatalog(List<ProductDisplayDTO> productDTOs);

    void displayPromotionQuantityMessage(String productName);

    void displayStockInsufficientMessage(String productName);

    void displayNonPromotionalPurchaseConfirmation(String productName, int quantity);

    void displayMembershipPrompt();

    void displayPurchaseResult(List<PromotionResult> results);

    void displayRetryMessage();
}
