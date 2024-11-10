package store.service;

import store.dao.ProductDAO;
import store.domain.entity.Product;
import store.dto.ProductDTO;
import store.dto.ProductDisplayDTO;
import store.domain.vo.Name;
import store.domain.PromotionType;
import store.domain.vo.Quantity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class StoreService {
    private final ProductDAO productDAO;

    public StoreService(ProductDAO productDAO) {
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

    private Map<Name, List<ProductDTO>> groupProductsByName(List<ProductDTO> productDTOS) {
        return productDTOS.stream()
                .collect(Collectors.groupingBy(ProductDTO::name, LinkedHashMap::new, Collectors.toList()));
    }

    private void addPromotionProducts(List<ProductDTO> products, List<ProductDisplayDTO> displayProductList) {
        boolean hasPromotion = products.stream()
                .anyMatch(p -> p.promotionType() != PromotionType.NONE);
        boolean hasRegularProduct = products.stream()
                .anyMatch(p -> p.promotionType() == PromotionType.NONE);

        addPromotionProductsToList(products, displayProductList);
        addOutOfStockForPromotionOnly(hasPromotion, hasRegularProduct, products, displayProductList);
    }

    private void addPromotionProductsToList(List<ProductDTO> products, List<ProductDisplayDTO> displayProductList) {
        products.stream()
                .filter(p -> p.promotionType() != PromotionType.NONE)
                .forEach(p -> displayProductList.add(
                        ProductDisplayDTO.of(p.name(), p.money(), p.quantity(), p.promotionType())));
    }

    private void addOutOfStockForPromotionOnly(boolean hasPromotion, boolean hasRegularProduct,
                                               List<ProductDTO> products, List<ProductDisplayDTO> displayProductList) {
        if (hasPromotion && !hasRegularProduct) {
            ProductDTO sampleProduct = products.getFirst();
            displayProductList.add(
                    ProductDisplayDTO.of(sampleProduct.name(), sampleProduct.money(), Quantity.newInstance(0),
                            PromotionType.NONE));
        }
    }

    private void addRegularProducts(List<ProductDTO> products, List<ProductDisplayDTO> displayProductList) {
        products.stream()
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
}
