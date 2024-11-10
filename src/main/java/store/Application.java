package store;

import store.config.AppConfig;
import store.controller.StoreController;
import store.io.CacheFileInitializer;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = AppConfig.getInstance();

        CacheFileInitializer cacheFileInitializer = appConfig.cacheFileInitializer();
        cacheFileInitializer.initializeProductsCacheFile();

        StoreController storeController = appConfig.storeController();
        storeController.run();
    }
}
