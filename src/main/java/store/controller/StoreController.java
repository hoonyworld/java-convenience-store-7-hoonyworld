package store.controller;

import java.util.List;
import store.dto.ProductDTO;
import store.dto.ProductDisplayDTO;
import store.service.StoreService;
import store.template.RetryTemplate;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final StoreService storeService;
    private final RetryTemplate retryTemplate;

    public StoreController(InputView inputView, OutputView outputView, StoreService storeService, RetryTemplate retryTemplate) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storeService = storeService;
        this.retryTemplate = retryTemplate;
    }

    public void run() {
        handleUserInput();
    }

    private void handleUserInput() {
        retryTemplate.execute(() -> {
            displayProducts();
//            String userInput = inputView.readUserInput();
//            processUserInput(userInput);
        });
    }

    private void displayProducts() {
        List<ProductDTO> productDTOS = storeService.findAllProducts();
        List<ProductDisplayDTO> productDisplayDTOS = storeService.findAllProductsForDisplay(productDTOS);
        outputView.displayProductCatalog(productDisplayDTOS);
    }
}
