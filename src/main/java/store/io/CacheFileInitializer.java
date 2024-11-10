package store.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CacheFileInitializer {

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
            System.err.println(e.getMessage());
        }
    }

    private boolean productsCacheFileExists() {
        return Files.exists(productsCacheFilePath);
    }

    private void createProductsCacheFile() throws IOException {
        Files.createFile(productsCacheFilePath);
    }

    private void clearProductsCacheFile() throws IOException {
        Files.writeString(productsCacheFilePath, "");
    }

    private void copyProductsFileToProductsCacheFile() throws IOException {
        Files.copy(productsFilePath, productsCacheFilePath, StandardCopyOption.REPLACE_EXISTING);
    }
}
