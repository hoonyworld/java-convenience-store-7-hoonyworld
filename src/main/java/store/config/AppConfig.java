package store.config;

import java.nio.file.Path;
import store.controller.StoreController;
import store.dao.ProductDAO;
import store.dao.PromotionDAO;
import store.io.CacheFileInitializer;
import store.service.StoreService;
import store.template.RetryTemplate;
import store.view.InputView;
import store.view.OutputView;
import store.view.console.ConsoleInputView;
import store.view.console.ConsoleOutputView;

public class AppConfig {
    private static final Path PRODUCTS_FILE_PATH = Path.of("src/main/resources/products.md");
    private static final Path PRODUCTS_CACHE_FILE_PATH = Path.of("src/main/resources/product_cache.md");
    private static final Path PROMOTIONS_FILE_PATH = Path.of("src/main/resources/promotions.md");

    private AppConfig() {
    }

    private static class SingleTonHelper {
        private static final AppConfig INSTANCE = new AppConfig();
    }

    public static AppConfig getInstance() {
        return SingleTonHelper.INSTANCE;
    }

    public CacheFileInitializer cacheFileInitializer() {
        return new CacheFileInitializer(PRODUCTS_FILE_PATH, PRODUCTS_CACHE_FILE_PATH);
    }

    public StoreController storeController() {
        return new StoreController(createInputView(), createOutputView(), createStoreService(), createRetryTemplate());
    }

    private InputView createInputView() {
        return new ConsoleInputView();
    }

    private OutputView createOutputView() {
        return new ConsoleOutputView();
    }

    private StoreService createStoreService() {
        return new StoreService(createProductDAO(), createPromotionDAO());
    }

    private ProductDAO createProductDAO() {
        return new ProductDAO(PRODUCTS_CACHE_FILE_PATH);
    }

    private PromotionDAO createPromotionDAO() {
        return new PromotionDAO(PROMOTIONS_FILE_PATH);
    }

    private RetryTemplate createRetryTemplate() {
        return new RetryTemplate(new ConsoleOutputView());
    }
}
