package com.education.hw.tests;

public class Order {
    private final int orderId;
    private final int quantity;
    private final double initPrice;

    public static int id = 0;

    public Order(double initPrice, String productName, int quantity) {
        this.orderId = ++id;
        this.initPrice = initPrice;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        if(quantity <= 0) {
            return 0.0;
        }
        return quantity * initPrice;
    }

    public int getOrderId() {
        return orderId;
    }
}
