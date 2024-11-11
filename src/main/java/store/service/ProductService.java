package store.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import store.dao.ProductDAO;
import store.domain.PromotionType;
import store.domain.entity.Product;
import store.domain.vo.Name;
import store.domain.vo.Quantity;
import store.dto.ProductDTO;
import store.dto.ProductDisplayDTO;
import store.dto.ProductSelectionDTO;
import store.exception.ArgumentErrorMessage;
import store.exception.StoreArgumentException;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<ProductDTO> findAllProducts() {
        return productDAO.findAll().stream()
                .map(ProductDTO::from)
                .collect(Collectors.toList());
    }

    public List<ProductDisplayDTO> findAllProductsForDisplay(List<ProductDTO> productDTOS) {
        Map<Name, List<ProductDTO>> groupedProducts = groupProductsByName(productDTOS);
        List<ProductDisplayDTO> displayProductList = new ArrayList<>();

        for (Map.Entry<Name, List<ProductDTO>> entry : groupedProducts.entrySet()) {
            List<ProductDTO> products = entry.getValue();

            addPromotionProducts(products, displayProductList);
            addRegularProducts(products, displayProductList);
        }

        return displayProductList;
    }

    public Product findProductByNameAsEntity(Name name) {
        return findProductByName(name).toEntity();
    }

    public void validateStockAvailability(ProductSelectionDTO selection, Product product) {
        if (product.isStockNotAvailable(selection.quantity())) {
            throw StoreArgumentException.from(ArgumentErrorMessage.INSUFFICIENT_STOCK);
        }
    }

    public void validateUniqueProductName(ProductSelectionDTO productSelectionDTO, Set<Name> productNames) {
        if (!productNames.add(productSelectionDTO.name())) {
            throw StoreArgumentException.from(ArgumentErrorMessage.INVALID_FORMAT);
        }
    }

    public void updateProductStock(List<Product> updatedProducts) {
        productDAO.updateAll(updatedProducts);
    }

    private Map<Name, List<ProductDTO>> groupProductsByName(List<ProductDTO> productDTOS) {
        return productDTOS.stream()
                .collect(Collectors.groupingBy(ProductDTO::name, LinkedHashMap::new, Collectors.toList()));
    }

    private void addPromotionProducts(List<ProductDTO> productDTOS, List<ProductDisplayDTO> displayDTOS) {
        boolean hasPromotion = productDTOS.stream()
                .anyMatch(p -> p.promotionType() != PromotionType.NONE);
        boolean hasRegularProduct = productDTOS.stream()
                .anyMatch(p -> p.promotionType() == PromotionType.NONE);

        addPromotionProductsToList(productDTOS, displayDTOS);
        addOutOfStockForPromotionOnly(hasPromotion, hasRegularProduct, productDTOS, displayDTOS);
    }

    private void addPromotionProductsToList(List<ProductDTO> productDTOS, List<ProductDisplayDTO> displayDTOS) {
        productDTOS.stream()
                .filter(p -> p.promotionType() != PromotionType.NONE)
                .forEach(p -> displayDTOS.add(
                        ProductDisplayDTO.of(p.name(), p.money(), p.quantity(), p.promotionType())));
    }

    private void addOutOfStockForPromotionOnly(boolean hasPromotion, boolean hasRegularProduct,
                                               List<ProductDTO> productDTOS, List<ProductDisplayDTO> displayDTOS) {
        if (hasPromotion && !hasRegularProduct) {
            ProductDTO sampleProduct = productDTOS.getFirst();
            displayDTOS.add(
                    ProductDisplayDTO.of(sampleProduct.name(), sampleProduct.money(), Quantity.newInstance(0),
                            PromotionType.NONE));
        }
    }

    private void addRegularProducts(List<ProductDTO> productDTOS, List<ProductDisplayDTO> displayProductList) {
        productDTOS.stream()
                .filter(p -> p.promotionType() == PromotionType.NONE)
                .forEach(p -> addRegularProductToList(p, displayProductList));
    }

    private void addRegularProductToList(ProductDTO p, List<ProductDisplayDTO> displayProductList) {
        removeOutOfStockRegularProduct(displayProductList, p);
        displayProductList.add(ProductDisplayDTO.of(p.name(), p.money(), p.quantity(), PromotionType.NONE));
    }

    private void removeOutOfStockRegularProduct(List<ProductDisplayDTO> displayProductList, ProductDTO p) {
        displayProductList.removeIf(
                d -> d.name().equals(p.name()) && d.promotionType() == PromotionType.NONE && d.quantity().isZero());
    }

    private ProductDTO findProductByName(Name name) {
        Product product = productDAO.findByName(name);
        return ProductDTO.from(product);
    }
}
