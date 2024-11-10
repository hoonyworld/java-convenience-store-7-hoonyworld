package store.config;

import java.nio.file.Path;
import store.io.CacheFileInitializer;

public class AppConfig {
    private static final Path PRODUCTS_FILE_PATH = Path.of("src/main/resources/products.md");
    private static final Path PRODUCTS_CACHE_FILE_PATH = Path.of("src/main/resources/product_cache.md");

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
}
