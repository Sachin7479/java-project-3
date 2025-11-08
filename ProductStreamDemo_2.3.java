import java.util.*;
import java.util.stream.*;
import java.util.function.*;

class Product {
    private String name;
    private double price;
    private String category;

    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    @Override
    public String toString() {
        return String.format("Product{name='%s', price=%.2f, category='%s'}",
                name, price, category);
    }
}

public class ProductStreamDemo {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
            new Product("Laptop", 1200.0, "Electronics"),
            new Product("Phone", 700.0, "Electronics"),
            new Product("T-Shirt", 25.0, "Apparel"),
            new Product("Jeans", 50.0, "Apparel"),
            new Product("Milk", 2.5, "Grocery"),
            new Product("Bread", 1.5, "Grocery")
        );
        Map<String, List<Product>> productsByCategory = products.stream()
            .collect(Collectors.groupingBy(Product::getCategory));
        System.out.println("Products Grouped by Category:");
        productsByCategory.forEach((cat, prods) -> System.out.println(cat + ": " + prods));
        Map<String, Optional<Product>> maxByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.maxBy(Comparator.comparingDouble(Product::getPrice))
            ));
        System.out.println("\nMost Expensive Product in Each Category:");
        maxByCategory.forEach((cat, prodOpt) -> System.out.println(cat + ": " + prodOpt.get()));
        double avgPrice = products.stream()
            .collect(Collectors.averagingDouble(Product::getPrice));
        System.out.println("\nAverage Price of All Products: " + avgPrice);
    }
}
