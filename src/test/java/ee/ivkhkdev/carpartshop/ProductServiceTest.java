package ee.ivkhkdev.carpartshop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {
    private ProductService productService;
    private final String testProductFile = "test_products.dat"; // Unique test file name

    @BeforeEach
    public void setUp() {
        productService = new ProductService(testProductFile);
    }

    @AfterEach
    public void tearDown() {
        // Delete the test file after each test
        File file = new File(testProductFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAddProduct() {
        // Arrange
        Scanner scanner = new Scanner("Oil Filter\n25.99\n");

        // Act
        productService.addProduct(scanner);

        // Assert
        List<Product> products = productService.getProducts();
        assertEquals(1, products.size());
        assertEquals("Oil Filter", products.get(0).name());
        assertEquals(25.99, products.get(0).price());
    }

    @Test
    public void testListProducts() {
        // Arrange
        Scanner scanner = new Scanner("Brake Pads\n49.99\n");
        productService.addProduct(scanner);

        // Act
        productService.listProducts();

        // Assert
        assertEquals(1, productService.getProducts().size());
    }

    @Test
    public void testSaveAndLoadProducts() throws Exception {
        // Arrange
        Scanner scanner1 = new Scanner("Battery\n75.00\n");
        Scanner scanner2 = new Scanner("Tire\n120.00\n");
        productService.addProduct(scanner1);
        productService.addProduct(scanner2);

        // Act
        productService.saveProducts();

        // Create a new instance of ProductService to test loading
        ProductService newProductService = new ProductService(testProductFile);

        // Assert
        assertEquals(2, newProductService.getProducts().size());
        assertEquals("Battery", newProductService.getProducts().get(0).name());
        assertEquals(75.00, newProductService.getProducts().get(0).price());
        assertEquals("Tire", newProductService.getProducts().get(1).name());
        assertEquals(120.00, newProductService.getProducts().get(1).price());
    }
}
