package store.view;

import java.util.List;
import store.dto.ProductDisplayDTO;

public interface OutputView {
    void printExceptionMessage(Exception e);

    void displayProductCatalog(List<ProductDisplayDTO> productDTOs);
}
