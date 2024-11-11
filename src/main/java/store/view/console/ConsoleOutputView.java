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

    private static final String PROMOTION_QUANTITY_MESSAGE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String NON_PROMOTIONAL_PURCHASE_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private static final String STOCK_INSUFFICIENT_MESSAGE = "[알림] %s에 대한 추가 혜택을 적용할 수 있는 재고가 부족합니다.";
    private static final String MEMBERSHIP_PROMPT = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String PURCHASE_RESULT_HEADER = "==============W 편의점================";
    private static final String PRODUCT_HEADER = "상품명\t\t수량\t금액";
    private static final String GIVEAWAY_HEADER = "=============증    정===============";
    private static final String FOOTER = "====================================";
    private static final String TOTAL_COST_LABEL = "총구매액\t\t\t%,d";
    private static final String DISCOUNT_LABEL = "행사할인\t\t\t-%,d";
    private static final String MEMBERSHIP_DISCOUNT_LABEL = "멤버십할인\t\t\t-%,d";
    private static final String FINAL_PRICE_LABEL = "내실돈\t\t\t%,d";
    private static final String RETRY_MESSAGE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    @Override
    public void printExceptionMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void displayProductCatalog(List<ProductDisplayDTO> productDisplayDTOS) {
        System.out.println(WELCOME_MESSAGE);
        System.out.println(CURRENT_STOCK_MESSAGE);
        productDisplayDTOS.forEach(product -> System.out.println(formatProduct(product)));
        System.out.println();
        System.out.println(INPUT_PROMPT);
    }

    @Override
    public void displayPromotionQuantityMessage(String productName) {
        System.out.println();
        System.out.println(String.format(PROMOTION_QUANTITY_MESSAGE, productName));
    }

    @Override
    public void displayNonPromotionalPurchaseConfirmation(String productName, int quantity) {
        System.out.println();
        System.out.println(String.format(NON_PROMOTIONAL_PURCHASE_MESSAGE, productName, quantity));
    }

    @Override
    public void displayStockInsufficientMessage(String productName) {
        System.out.println();
        System.out.println(String.format(STOCK_INSUFFICIENT_MESSAGE, productName));
    }

    @Override
    public void displayMembershipPrompt() {
        System.out.println();
        System.out.println(MEMBERSHIP_PROMPT);
    }

    @Override
    public void displayPurchaseResult(List<PromotionResult> results) {
        System.out.println();
        printHeader();

        int totalCost = 0;
        int totalDiscount = 0;
        int membershipDiscount = 0;
        StringBuilder giveawayItems = new StringBuilder(GIVEAWAY_HEADER + "\n");

        for (PromotionResult result : results) {
            totalCost = processItem(result, totalCost);
            totalDiscount += result.getDiscount();
            membershipDiscount += result.getMembershipDiscount();
            appendGiveawayItems(result, giveawayItems);
        }

        printResults(giveawayItems, totalCost, totalDiscount, membershipDiscount);
    }

    @Override
    public void displayRetryMessage() {
        System.out.println();
        System.out.println(RETRY_MESSAGE);
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

    private void printHeader() {
        System.out.println(PURCHASE_RESULT_HEADER);
        System.out.println(PRODUCT_HEADER);
    }

    private int processItem(PromotionResult result, int totalCost) {
        int itemCost = result.getTotalQuantity() * result.getPricePerUnit();
        totalCost += itemCost;
        System.out.printf("%s\t\t%d\t%,d\n", result.getProductName(), result.getTotalQuantity(), itemCost);
        return totalCost;
    }

    private void appendGiveawayItems(PromotionResult result, StringBuilder giveawayItems) {
        if (result.hasPromotion() && result.getPromotionalQuantity() > 0) {
            giveawayItems.append(String.format("%s\t\t%d\n", result.getProductName(), result.getPromotionalQuantity()));
        }
    }

    private void printResults(StringBuilder giveawayItems, int totalCost, int totalDiscount, int membershipDiscount) {
        if (giveawayItems.length() > GIVEAWAY_HEADER.length()) {
            giveawayItems.setLength(giveawayItems.length() - 1);
            System.out.println(giveawayItems);
        }

        int finalPrice = totalCost - totalDiscount - membershipDiscount;
        System.out.println(FOOTER);
        printFinalDetails(totalCost, totalDiscount, membershipDiscount, finalPrice);
    }

    private void printFinalDetails(int totalCost, int totalDiscount, int membershipDiscount, int finalPrice) {
        System.out.printf(TOTAL_COST_LABEL, totalCost);
        System.out.printf("\n" + DISCOUNT_LABEL, totalDiscount);
        System.out.printf("\n" + MEMBERSHIP_DISCOUNT_LABEL, membershipDiscount);
        System.out.printf("\n" + FINAL_PRICE_LABEL, finalPrice);
        System.out.println("\n" + FOOTER);
    }
}
