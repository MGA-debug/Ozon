package ru.ozon.framework.utils;

import java.util.ArrayList;
import java.util.List;

public class Product {
    public static List<Product> productList = new ArrayList<>();
    String title;
    int price;
    static int totalPrice;

    public Product(String title, int price) {
        this.title = title;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }

    public static void addListProduct(String name, int price) {
        productList.add(new Product(name,price));
    }

    public static int totalProductPrice() {
        totalPrice = 0;
        for (Product product: productList) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

}
