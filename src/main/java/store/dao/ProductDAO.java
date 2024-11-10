package store.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.domain.vo.Name;
import store.domain.PromotionType;
import store.domain.vo.Money;
import store.domain.vo.Quantity;
import store.dto.ProductDTO;
import store.exception.StateErrorMessage;
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

    public List<ProductDTO> findAll() {
        try (Stream<String> lines = Files.lines(productsFilePath)) {
            return lines.filter(line -> !line.startsWith(NAME))
                    .map(this::parseProduct)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw StoreStateException.from(StateErrorMessage.FILE_OPERATION_ERROR);
        }
    }

    private ProductDTO parseProduct(String line) {
        List<String> parts = List.of(line.split(DELIMITER));

        Name name = Name.newInstance(parts.get(NAME_INDEX));
        Money money = Money.newInstance(Integer.parseInt(parts.get(MONEY_INDEX)));
        Quantity quantity = Quantity.newInstance(Integer.parseInt(parts.get(QUANTITY_INDEX)));
        PromotionType promotionType = PromotionType.from(parts.get(PROMOTION_INDEX).trim());

        return ProductDTO.of(name, money, quantity, promotionType);
    }
}
