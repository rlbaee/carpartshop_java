package ee.ivkhkdev.carpartshop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerService {
    private final List<Customer> customers;
    private static final String CUSTOMER_FILE = "customers.dat";

    public CustomerService() {
        customers = loadCustomers();
    }

    public void addCustomer(Scanner scanner) {
        System.out.print("Введите имя клиента: ");
        String name = scanner.nextLine();
        customers.add(new Customer(name));
        System.out.println("Клиент добавлен!");
    }

    public void listCustomers() {
        if (customers.isEmpty()) {
            System.out.println("Список клиентов пуст.");
            return;
        }
        System.out.println("Список клиентов:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, customers.get(i).name());
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void saveCustomers() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CUSTOMER_FILE))) {
            oos.writeObject(customers);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Customer> loadCustomers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CUSTOMER_FILE))) {
            return (List<Customer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
