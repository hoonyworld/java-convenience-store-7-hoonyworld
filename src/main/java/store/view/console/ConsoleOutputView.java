package store.view.console;

import java.util.List;
import store.domain.PromotionType;
import store.dto.ProductDisplayDTO;
import store.dto.PromotionResult;
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

    @Override
    public void displayPromotionQuantityMessage(String productName) {
        System.out.println("현재 " + productName + "은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
    }

    @Override
    public void displayNonPromotionalPurchaseConfirmation(String productName, int quantity) {
        System.out.println("현재 " + productName + " " + quantity + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
    }

    @Override
    public void displayStockInsufficientMessage(String productName) {
        System.out.println("[알림] " + productName + "에 대한 추가 혜택을 적용할 수 있는 재고가 부족합니다.");
    }

    @Override
    public void displayMembershipPrompt() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
    }

    @Override
    public void displayPurchaseResult(List<PromotionResult> results) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");

        int totalCost = 0;
        int totalDiscount = 0;
        int membershipDiscount = 0;

        StringBuilder giveawayItems = new StringBuilder("=============증    정===============\n");

        for (PromotionResult result : results) {
            int itemCost = result.getTotalQuantity() * result.getPricePerUnit();
            int itemDiscountedCost = (result.getTotalQuantity() - result.getPromotionalQuantity()) * result.getPricePerUnit();

            totalCost += itemCost;
            totalDiscount += result.getDiscount();
            membershipDiscount += result.getMembershipDiscount();

            System.out.printf("%s\t\t%d\t%,d\n", result.getProductName(), result.getTotalQuantity(), itemCost);

            if (result.hasPromotion() && result.getPromotionalQuantity() > 0) {
                giveawayItems.append(String.format("%s\t\t%d\n", result.getProductName(), result.getPromotionalQuantity()));
            }
        }

        if (giveawayItems.length() > "=============증    정===============\n".length()) {
            giveawayItems.setLength(giveawayItems.length() - 1);
            System.out.println(giveawayItems);
        }

        int finalPrice = totalCost - totalDiscount - membershipDiscount;

        System.out.println("====================================");
        System.out.printf("총구매액\t\t\t%,d\n", totalCost);
        System.out.printf("행사할인\t\t\t-%,d\n", totalDiscount);
        System.out.printf("멤버십할인\t\t\t-%,d\n", membershipDiscount);
        System.out.printf("내실돈\t\t\t%,d\n", finalPrice);
        System.out.println("====================================");
    }

    @Override
    public void displayRetryMessage() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
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
