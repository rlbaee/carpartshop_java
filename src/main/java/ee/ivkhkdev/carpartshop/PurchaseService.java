package ee.ivkhkdev.carpartshop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PurchaseService {
    private final List<PurchasedProduct> purchases;
    private static final String PURCHASE_FILE = "purchases.dat";

    public PurchaseService() {
        purchases = loadPurchases();
    }

    public void purchaseProduct(Scanner scanner, CustomerService customerService, ProductService productService) {
        customerService.listCustomers();
        System.out.print("Выберите номер клиента: ");
        int customerIndex = Integer.parseInt(scanner.nextLine()) - 1;
        productService.listProducts();
        System.out.print("Выберите номер товара: ");
        int productIndex = Integer.parseInt(scanner.nextLine()) - 1;
        purchases.add(new PurchasedProduct(customerService.getCustomers().get(customerIndex), productService.getProducts().get(productIndex)));
        System.out.println("Покупка совершена!");
    }

    public void listPurchasedProducts() {
        if (purchases.isEmpty()) {
            System.out.println("Список покупок пуст.");
            return;
        }
        System.out.println("Список покупок:");
        for (int i = 0; i < purchases.size(); i++) {
            System.out.printf("%d. Клиент: %s, Товар: %s%n", i + 1, purchases.get(i).customer().name(), purchases.get(i).product().name());
        }
    }

    public void savePurchases() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PURCHASE_FILE))) {
            oos.writeObject(purchases);
        }
    }

    @SuppressWarnings("unchecked")
    private List<PurchasedProduct> loadPurchases() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PURCHASE_FILE))) {
            return (List<PurchasedProduct>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
