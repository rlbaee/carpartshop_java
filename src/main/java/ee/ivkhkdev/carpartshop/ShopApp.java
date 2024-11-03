package ee.ivkhkdev.carpartshop;

import java.io.IOException;
import java.util.Scanner;

public class ShopApp {
    private final Scanner scanner;
    private final ProductService productService;
    private final CustomerService customerService;
    private final PurchaseService purchaseService;

    public ShopApp(Scanner scanner) {
        this.scanner = scanner;
        productService = new ProductService("products.txt");
        customerService = new CustomerService();
        purchaseService = new PurchaseService();
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n0. Выйти\n1. Добавить товар\n2. Список товаров\n3. Добавить клиента\n4. Список клиентов\n5. Купить товар\n6. Список покупок");
            System.out.print("Выберите номер задачи: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 0 -> running = false;
                case 1 -> productService.addProduct(scanner);
                case 2 -> productService.listProducts();
                case 3 -> customerService.addCustomer(scanner);
                case 4 -> customerService.listCustomers();
                case 5 -> purchaseService.purchaseProduct(scanner, customerService, productService);
                case 6 -> purchaseService.listPurchasedProducts();
                default -> System.out.println("Некорректный выбор!");
            }
        }

        // Save data before exiting
        try {
            productService.saveProducts();
            customerService.saveCustomers();
            purchaseService.savePurchases();
        } catch (IOException e) {
            System.out.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }
}
