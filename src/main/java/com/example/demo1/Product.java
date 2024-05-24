package com.example.demo1;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private int areaId;

    public Product(int id, String name, double price, int quantity, int areaId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.areaId = areaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
}
