package store.util;

import java.util.Arrays;
import java.util.List;
import store.domain.vo.Name;
import store.domain.vo.Quantity;
import store.dto.ProductSelectionDTO;
import store.exception.ArgumentErrorMessage;
import store.exception.StoreArgumentException;

public class Parser {

    private Parser() {
    }

    public static ProductSelectionDTO parseSelection(String selection, String delimiter) {
        List<String> parts = Arrays.asList(cleanSelection(selection).split(delimiter));

        validatePartsSizeTwo(parts);
        validatePartNotBlank(parts.get(0));
        validatePartNotBlank(parts.get(1));
        validateQuantityFormat(parts);

        Name name = Name.newInstance(parts.get(0).trim());
        Quantity quantity = Quantity.newInstance(Integer.parseInt(parts.get(1).trim()));

        return ProductSelectionDTO.of(name, quantity);
    }

    private static String cleanSelection(String selection) {
        return selection.replaceAll("[\\[\\]]", "");
    }

    private static void validatePartsSizeTwo(List<String> parts) {
        if (parts.size() < 2) {
            throw StoreArgumentException.from(ArgumentErrorMessage.INVALID_FORMAT);
        }
    }

    private static void validatePartNotBlank(String part) {
        if (part.isBlank()) {
            throw StoreArgumentException.from(ArgumentErrorMessage.INVALID_FORMAT);
        }
    }

    private static void validateQuantityFormat(List<String> parts) {
        if (parts.get(1).contains(".") || Integer.parseInt(parts.get(1)) <= 0) {
            throw StoreArgumentException.from(ArgumentErrorMessage.INVALID_FORMAT);
        }
    }
}
