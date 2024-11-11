package store.controller;

import java.util.List;
import store.dto.ProductDTO;
import store.dto.ProductDisplayDTO;
import store.service.ProductService;
import store.view.OutputView;

public class ProductController {
    private final ProductService productService;
    private final OutputView outputView;

    public ProductController(ProductService productService, OutputView outputView) {
        this.productService = productService;
        this.outputView = outputView;
    }

    public void displayProducts() {
        List<ProductDTO> productDTOS = productService.findAllProducts();
        List<ProductDisplayDTO> productDisplayDTOS = productService.findAllProductsForDisplay(productDTOS);
        outputView.displayProductCatalog(productDisplayDTOS);
    }
}
