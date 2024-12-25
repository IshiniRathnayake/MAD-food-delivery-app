package com.example.app3;
public class PizzaItem {
    private String name;
    private int price;
    private int quantity;

    public PizzaItem(String name, int price) {
        this.name = name;
        this.price = price;
        this.quantity = 0;  // Default quantity is 0
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
