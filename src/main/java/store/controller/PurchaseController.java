package store.controller;

import java.util.List;
import store.dto.ProductSelectionDTO;
import store.service.StoreService;
import store.view.InputView;

public class PurchaseController {
    private final InputView inputView;
    private final StoreService storeService;

    public PurchaseController(InputView inputView, StoreService storeService) {
        this.inputView = inputView;
        this.storeService = storeService;
    }

    public void handlePurchase() {
        List<ProductSelectionDTO> productSelectionDTOS = inputView.readProductSelections();
        storeService.processPurchase(productSelectionDTOS);
    }
}
