package store.view;

import java.util.List;
import store.dto.ProductSelectionDTO;

public interface InputView {
    List<ProductSelectionDTO> readProductSelections();
}
