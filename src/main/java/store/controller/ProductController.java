package store.controller;

import java.util.List;
import store.dto.ProductDTO;
import store.dto.ProductDisplayDTO;
import store.service.ProductService;
import store.template.RetryTemplate;
import store.view.OutputView;

public class ProductController {
    private final ProductService productService;
    private final OutputView outputView;
    private final RetryTemplate retryTemplate;

    public ProductController(ProductService productService, OutputView outputView, RetryTemplate retryTemplate) {
        this.productService = productService;
        this.outputView = outputView;
        this.retryTemplate = retryTemplate;
    }

    public void displayProducts() {
        retryTemplate.execute(() -> {
            List<ProductDTO> productDTOS = productService.findAllProducts();
            List<ProductDisplayDTO> productDisplayDTOS = productService.findAllProductsForDisplay(productDTOS);
            outputView.displayProductCatalog(productDisplayDTOS);
        });
    }
}
