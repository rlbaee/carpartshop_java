package ee.ivkhkdev.carpartshop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseServiceTest {
    private ProductService productService;
    private CustomerService customerService;
    private PurchaseService purchaseService;
    private final String testPurchaseFile = "purchases.dat"; // To handle the test file separately

    @BeforeEach
    public void setUp() {
        // Initialize services
        productService = new ProductService("products.txt");
        customerService = new CustomerService();

        // Add test products
        productService.addProduct(new Scanner("Oil Filter\n25.99\n"));
        productService.addProduct(new Scanner("Brake Pads\n49.99\n"));

        // Add test customers
        customerService.addCustomer(new Scanner("John Doe\n"));
        customerService.addCustomer(new Scanner("Jane Smith\n"));

        // Initialize PurchaseService
        purchaseService = new PurchaseService();
    }

    @AfterEach
    public void tearDown() {
        // Delete the test purchase file after each test
        File file = new File(testPurchaseFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testPurchaseProduct() {
        // Arrange
        Scanner scanner = new Scanner("1\n1\n"); // Select first customer and first product

        // Act
        purchaseService.purchaseProduct(scanner, customerService, productService);

        // Assert
        List<PurchasedProduct> purchases = purchaseService.getPurchases();
        assertEquals(1, purchases.size());
        assertEquals("John Doe", purchases.get(0).customer().name());
        assertEquals("Oil Filter", purchases.get(0).product().name());
    }

    @Test
    public void testListPurchasedProducts() {
        // Arrange
        Scanner scanner = new Scanner("1\n1\n"); // Select first customer and first product
        purchaseService.purchaseProduct(scanner, customerService, productService);

        // Act
        purchaseService.listPurchasedProducts(); // Should print the purchase details

        // Assert
        assertEquals(1, purchaseService.getPurchases().size());
    }

    @Test
    public void testSaveAndLoadPurchases() throws Exception {
        // Arrange
        Scanner scanner1 = new Scanner("1\n1\n"); // Select first customer and first product
        Scanner scanner2 = new Scanner("2\n2\n"); // Select second customer and second product
        purchaseService.purchaseProduct(scanner1, customerService, productService);
        purchaseService.purchaseProduct(scanner2, customerService, productService);

        // Act
        purchaseService.savePurchases();

        // Create a new instance of PurchaseService to test loading
        PurchaseService newPurchaseService = new PurchaseService();

        // Assert
        assertEquals(2, newPurchaseService.getPurchases().size());
        assertEquals("John Doe", newPurchaseService.getPurchases().get(0).customer().name());
        assertEquals("Oil Filter", newPurchaseService.getPurchases().get(0).product().name());
        assertEquals("Jane Smith", newPurchaseService.getPurchases().get(1).customer().name());
        assertEquals("Brake Pads", newPurchaseService.getPurchases().get(1).product().name());
    }

    @Test
    public void testEmptyPurchasesList() {
        // Act
        purchaseService.listPurchasedProducts(); // Should print "Список покупок пуст."

        // Assert
        assertTrue(purchaseService.getPurchases().isEmpty());
    }
}
