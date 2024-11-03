package ee.ivkhkdev.carpartshop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductService {
    private final List<Product> products;
    private final String productFile;

    public ProductService(String productFile) {
        this.productFile = productFile;
        products = loadProducts();
    }

    public void addProduct(Scanner scanner) {
        System.out.print("Введите название товара: ");
        String name = scanner.nextLine();
        System.out.print("Введите цену товара: ");
        double price = Double.parseDouble(scanner.nextLine());
        products.add(new Product(name, price));
        System.out.println("Товар добавлен!");
    }

    public void listProducts() {
        if (products.isEmpty()) {
            System.out.println("Список товаров пуст.");
            return;
        }
        System.out.println("Список товаров:");
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%d. %s - %.2f€%n", i + 1, products.get(i).name(), products.get(i).price());
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void saveProducts() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(productFile))) {
            oos.writeObject(products);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Product> loadProducts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(productFile))) {
            return (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
