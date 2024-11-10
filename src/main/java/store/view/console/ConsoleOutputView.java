package store.view.console;

import java.util.List;
import store.domain.PromotionType;
import store.dto.ProductDisplayDTO;
import store.view.OutputView;

public class ConsoleOutputView implements OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String CURRENT_STOCK_MESSAGE = "현재 보유하고 있는 상품입니다.";
    private static final String INPUT_PROMPT = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String NO_STOCK = "재고 없음";
    private static final String SPACE = " ";
    private static final String DASH = "-";

    @Override
    public void printExceptionMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void displayProductCatalog(List<ProductDisplayDTO> productDisplayDTOS) {
        System.out.println(WELCOME_MESSAGE);
        System.out.println(CURRENT_STOCK_MESSAGE);
        System.out.println();

        for (ProductDisplayDTO product : productDisplayDTOS) {
            System.out.println(formatProduct(product));
        }

        System.out.println();
        System.out.println(INPUT_PROMPT);
    }

    private String formatProduct(ProductDisplayDTO product) {
        StringBuilder productInfo = new StringBuilder(DASH + SPACE)
                .append(product.name() + SPACE)
                .append(product.money() + SPACE)
                .append(formatQuantity(product));
        formatPromotion(product, productInfo);

        return productInfo.toString();
    }

    private String formatQuantity(ProductDisplayDTO product) {
        if (product.quantity().isZero()) {
            return NO_STOCK;
        }
        return product.quantity().toString();
    }

    private void formatPromotion(ProductDisplayDTO product, StringBuilder productInfo) {
        if (product.promotionType() != PromotionType.NONE) {
            productInfo.append(SPACE).append(product.promotionType().getDescription());
        }
    }
}
