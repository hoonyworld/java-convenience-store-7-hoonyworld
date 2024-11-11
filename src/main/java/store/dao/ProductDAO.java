package store.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
            List<String> lines = Files.readAllLines(productsFilePath);
            List<String> updatedLines = new ArrayList<>();
            updatedLines.add(lines.get(0));

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                updatedLines.add(updateProductIfChanged(line, updatedProducts));
            }

            Files.write(productsFilePath, updatedLines);
        } catch (IOException e) {
            handleFileOperationException(e);
        }
    }

    private String updateProductIfChanged(String line, List<Product> updatedProducts) {
        String[] fields = line.split(",");
        Product product = createProductFromFields(fields);

        for (Product updatedProduct : updatedProducts) {
            if (isProductUpdated(product, updatedProduct)) {
                return updatedProductToString(updatedProduct);
            }
        }
        return line;
    }

    private Product createProductFromFields(String[] fields) {
        String productName = fields[0];
        int price = Integer.parseInt(fields[1]);
        int quantity = Integer.parseInt(fields[2]);
        String promotion = fields[3];
        return Product.create(Name.newInstance(productName), Money.newInstance(price), Quantity.newInstance(quantity), PromotionType.from(promotion));
    }

    private boolean isProductUpdated(Product product, Product updatedProduct) {
        boolean isPromotionTypeEqual = (product.getPromotionType() == null && updatedProduct.getPromotionType() == null)
                || (product.getPromotionType() != null && updatedProduct.getPromotionType() != null
                && product.getPromotionType().equals(updatedProduct.getPromotionType()));
        return product.getName().equals(updatedProduct.getName())
                && product.getMoney().equals(updatedProduct.getMoney())
                && !product.getQuantity().equals(updatedProduct.getQuantity()) // 수량 비교
                && isPromotionTypeEqual;
    }

    private void handleFileOperationException(IOException e) {
        System.err.println("파일 작업 중 오류 발생: " + e.getMessage());
        throw StoreStateException.from(StateErrorMessage.FILE_OPERATION_ERROR);
    }

    private String updatedProductToString(Product product) {
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
