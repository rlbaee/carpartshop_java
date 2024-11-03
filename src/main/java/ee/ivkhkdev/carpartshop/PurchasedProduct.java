package ee.ivkhkdev.carpartshop;

import java.io.Serializable;

public record PurchasedProduct(Customer customer, Product product) implements Serializable {}
