package store.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import store.exception.StoreArgumentException;
import store.template.RetryTemplate;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final ProductController productController;
    private final PurchaseController purchaseController;
    private final InputView inputView;
    private final OutputView outputView;
    private final RetryTemplate retryTemplate;

    public StoreController(ProductController productController, PurchaseController purchaseController, InputView inputView, OutputView outputView, RetryTemplate retryTemplate) {
        this.productController = productController;
        this.purchaseController = purchaseController;
        this.inputView = inputView;
        this.outputView = outputView;
        this.retryTemplate = retryTemplate;
    }

    public void run() {

            productController.displayProducts();

            purchaseController.handlePurchaseWithRetryDisplay();

//            boolean isShopping = handleShopping();

//            if (!isShopping) {
////                saveToOriginalFile();
//                break;
//            }

    }

    private boolean handleShopping() {
        return retryTemplate.execute(() -> {
            try {
                return inputView.readUserConfirmation();
            } catch (StoreArgumentException e) {
                outputView.printExceptionMessage(e);
                throw e;
            }
        });
    }

    private void saveToOriginalFile() {
        try {
            Path productsFilePath = Paths.get("src/main/resources/products.md");
            Path cacheFilePath = Paths.get("src/main/resources/products_cache.md");

            Files.copy(cacheFilePath, productsFilePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            outputView.printExceptionMessage(e);
        }
    }
}
