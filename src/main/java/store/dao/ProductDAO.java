package store.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.domain.entity.Product;
import store.domain.vo.Name;
import store.domain.PromotionType;
import store.domain.vo.Money;
import store.domain.vo.Quantity;
import store.exception.ArgumentErrorMessage;
import store.exception.StateErrorMessage;
import store.exception.StoreArgumentException;
import store.exception.StoreStateException;

public class ProductDAO {
    private static final int NAME_INDEX = 0;
    private static final int MONEY_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int PROMOTION_INDEX = 3;
    private static final String DELIMITER = ",";
    private static final String NAME = "name";

    private final Path productsFilePath;

    public ProductDAO(Path productsFilePath) {
        this.productsFilePath = productsFilePath;
    }

    public List<Product> findAll() {
        try (Stream<String> lines = Files.lines(productsFilePath)) {
            return lines.filter(line -> !line.startsWith(NAME))
                    .map(this::parseProduct)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw StoreStateException.from(StateErrorMessage.FILE_OPERATION_ERROR);
        }
    }

    public Product findByName(Name name) {
        return findAll().stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> StoreArgumentException.from(ArgumentErrorMessage.PRODUCT_NOT_FOUND));
    }

    public void updateAll(List<Product> updatedProducts) {
        try {
            List<String> updatedLines = findAll().stream()
                    .map(product -> {
                        // 변경된 제품을 업데이트된 리스트에서 찾음
                        for (Product updatedProduct : updatedProducts) {
                            if (product.getName().equals(updatedProduct.getName()) && !product.equals(updatedProduct)) {
                                // 재고가 변경되었으면 새로 업데이트된 정보를 반영
                                return updatedProductToString(updatedProduct);
                            }
                        }
                        // 변경되지 않은 제품은 원래 그대로 반환
                        return productToString(product);
                    })
                    .collect(Collectors.toList());

            // 변경된 데이터를 파일에 다시 저장
            Files.write(productsFilePath, updatedLines);
        } catch (IOException e) {
            throw StoreStateException.from(StateErrorMessage.FILE_OPERATION_ERROR);
        }
    }

    private String updatedProductToString(Product product) {
        return String.join(DELIMITER,
                product.getName().toString(),
                String.valueOf(product.getMoney().getPrice()),
                String.valueOf(product.getQuantity().getAmount()),
                product.getPromotionType().toString());
    }

    private String productToString(Product product) {
        return String.join(DELIMITER,
                product.getName().toString(),
                String.valueOf(product.getMoney().getPrice()),
                String.valueOf(product.getQuantity().getAmount()),
                product.getPromotionType().toString());
    }

    private Product parseProduct(String line) {
        List<String> parts = List.of(line.split(DELIMITER));

        Name name = Name.newInstance(parts.get(NAME_INDEX));
        Money money = Money.newInstance(Integer.parseInt(parts.get(MONEY_INDEX)));
        Quantity quantity = Quantity.newInstance(Integer.parseInt(parts.get(QUANTITY_INDEX)));
        PromotionType promotionType = PromotionType.from(parts.get(PROMOTION_INDEX).trim());

        return Product.create(name, money, quantity, promotionType);
    }
}
