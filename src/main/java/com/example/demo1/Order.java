package com.example.demo1;

import java.sql.Date;

public class Order {
    private int id;
    private int customerId;
    private Date orderDate;
    private double totalPrice;

    public Order(int id, int customerId, Date orderDate, double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
