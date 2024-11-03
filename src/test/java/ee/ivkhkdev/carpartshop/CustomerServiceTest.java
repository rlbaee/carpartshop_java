package ee.ivkhkdev.carpartshop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {
    private CustomerService customerService;
    private final String testCustomerFile = "customers.dat"; // To handle test file separately

    @BeforeEach
    public void setUp() {
        customerService = new CustomerService();
    }

    @AfterEach
    public void tearDown() {
        // Delete the test file after each test
        File file = new File(testCustomerFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAddCustomer() {
        // Arrange
        Scanner scanner = new Scanner("John Doe\n");

        // Act
        customerService.addCustomer(scanner);

        // Assert
        List<Customer> customers = customerService.getCustomers();
        assertEquals(1, customers.size());
        assertEquals("John Doe", customers.get(0).name());
    }

    @Test
    public void testListCustomers() {
        // Arrange
        Scanner scanner = new Scanner("Jane Doe\n");
        customerService.addCustomer(scanner);

        // Act
        customerService.listCustomers();

        // Assert
        assertEquals(1, customerService.getCustomers().size());
    }

    @Test
    public void testSaveAndLoadCustomers() throws Exception {
        // Arrange
        Scanner scanner1 = new Scanner("Alice\n");
        Scanner scanner2 = new Scanner("Bob\n");
        customerService.addCustomer(scanner1);
        customerService.addCustomer(scanner2);

        // Act
        customerService.saveCustomers();

        // Create a new instance of CustomerService to test loading
        CustomerService newCustomerService = new CustomerService();

        // Assert
        assertEquals(2, newCustomerService.getCustomers().size());
        assertEquals("Alice", newCustomerService.getCustomers().get(0).name());
        assertEquals("Bob", newCustomerService.getCustomers().get(1).name());
    }
}
