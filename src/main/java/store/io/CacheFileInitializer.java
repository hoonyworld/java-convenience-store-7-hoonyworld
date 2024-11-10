package store.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import store.exception.StateErrorMessage;
import store.exception.StoreStateException;

public class CacheFileInitializer {
    private static final String EMPTY_CONTENT = "";

    private final Path productsFilePath;
    private final Path productsCacheFilePath;

    public CacheFileInitializer(Path productsFilePath, Path productsCacheFilePath) {
        this.productsFilePath = productsFilePath;
        this.productsCacheFilePath = productsCacheFilePath;
    }

    public void initializeProductsCacheFile() {
        try {
            if (!productsCacheFileExists()) {
                createProductsCacheFile();
            }
            clearProductsCacheFile();
            copyProductsFileToProductsCacheFile();
        } catch (IOException e) {
            throw StoreStateException.from(StateErrorMessage.FILE_OPERATION_ERROR);
        }
    }

    private boolean productsCacheFileExists() {
        return Files.exists(productsCacheFilePath);
    }

    private void createProductsCacheFile() throws IOException {
        Files.createFile(productsCacheFilePath);
    }

    private void clearProductsCacheFile() throws IOException {
        Files.writeString(productsCacheFilePath, EMPTY_CONTENT);
    }

    private void copyProductsFileToProductsCacheFile() throws IOException {
        Files.copy(productsFilePath, productsCacheFilePath, StandardCopyOption.REPLACE_EXISTING);
    }
}
