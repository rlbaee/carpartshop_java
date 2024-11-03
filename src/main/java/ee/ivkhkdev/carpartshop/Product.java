package ee.ivkhkdev.carpartshop;

import java.io.Serializable;

public record Product(String name, double price) implements Serializable {}
