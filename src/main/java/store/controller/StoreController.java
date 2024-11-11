package store.controller;

import store.template.RetryTemplate;

public class StoreController {
    private final ProductController productController;
    private final PurchaseController purchaseController;
    private final RetryTemplate retryTemplate;

    public StoreController(ProductController productController, PurchaseController purchaseController,
                           RetryTemplate retryTemplate) {
        this.productController = productController;
        this.purchaseController = purchaseController;
        this.retryTemplate = retryTemplate;
    }

    public void run() {
        retryTemplate.execute(() -> {
            productController.displayProducts();
            purchaseController.handlePurchase();
        });
    }
}
