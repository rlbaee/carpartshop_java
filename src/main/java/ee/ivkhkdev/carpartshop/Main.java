package ee.ivkhkdev.carpartshop;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShopApp app = new ShopApp(scanner);
        app.run();
    }
}
