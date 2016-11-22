package com.company;

public class Order {
    private String name;
    private long price;

    public Order(String name, long price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }
}
