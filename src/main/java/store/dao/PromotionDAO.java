package store.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.domain.PromotionType;
import store.domain.entity.Promotion;
import store.exception.ArgumentErrorMessage;
import store.exception.StateErrorMessage;
import store.exception.StoreArgumentException;
import store.exception.StoreStateException;

public class PromotionDAO {
    private static final int NAME_INDEX = 0;
    private static final int BUY_INDEX = 1;
    private static final int GET_INDEX = 2;
    private static final int START_DATE_INDEX = 3;
    private static final int END_DATE_INDEX = 4;
    private static final String DELIMITER = ",";
    private static final String HEADER = "name";

    private final Path promotionsFilePath;

    public PromotionDAO(Path promotionsFilePath) {
        this.promotionsFilePath = promotionsFilePath;
    }

    public List<Promotion> findAll() {
        try (Stream<String> lines = Files.lines(promotionsFilePath)) {
            return lines.filter(line -> !line.startsWith(HEADER))
                    .map(this::parsePromotion)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw StoreStateException.from(StateErrorMessage.FILE_OPERATION_ERROR);
        }
    }

    private Promotion parsePromotion(String line) {
        List<String> parts = List.of(line.split(DELIMITER));

        PromotionType name = PromotionType.from(parts.get(NAME_INDEX));
        int buy = parseInteger(parts.get(BUY_INDEX), ArgumentErrorMessage.INVALID_PROMOTION_FORMAT);
        int get = parseInteger(parts.get(GET_INDEX), ArgumentErrorMessage.INVALID_PROMOTION_FORMAT);
        LocalDate startDate = parseDate(parts.get(START_DATE_INDEX), ArgumentErrorMessage.INVALID_DATE_FORMAT);
        LocalDate endDate = parseDate(parts.get(END_DATE_INDEX), ArgumentErrorMessage.INVALID_DATE_FORMAT);

        return Promotion.create(name, buy, get, startDate, endDate);
    }

    private int parseInteger(String value, ArgumentErrorMessage errorMessage) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw StoreArgumentException.from(errorMessage);
        }
    }

    private LocalDate parseDate(String date, ArgumentErrorMessage errorMessage) {
        try {
            return LocalDate.parse(date.trim());
        } catch (Exception e) {
            throw StoreArgumentException.from(errorMessage);
        }
    }
}
