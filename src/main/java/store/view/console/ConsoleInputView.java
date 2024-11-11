package store.view.console;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.dto.ProductSelectionDTO;
import store.exception.ArgumentErrorMessage;
import store.exception.StoreArgumentException;
import store.util.Parser;
import store.view.InputView;

public class ConsoleInputView implements InputView {
    private static final String DELIMITER_COMMA = ",";
    private static final String DELIMITER_DASH = "-";

    @Override
    public List<ProductSelectionDTO> readProductSelections() {
        String input = Console.readLine();

        if (input.isBlank()) {
            throw StoreArgumentException.from(ArgumentErrorMessage.INVALID_INPUT);
        }

        return Stream.of(input.split(DELIMITER_COMMA))
                .map(selection -> Parser.parseSelection(selection, DELIMITER_DASH))
                .collect(Collectors.toList());
    }

    public boolean readUserConfirmation() {
        String input = Console.readLine().toUpperCase();
        if (!input.equals("Y") && !input.equals("N")) {
            throw StoreArgumentException.from(ArgumentErrorMessage.INVALID_INPUT);
        }
        return input.equals("Y");
    }
}
