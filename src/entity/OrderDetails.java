package entity;

import java.io.Serializable;

public class OrderDetails implements Serializable {
    private Orders orderId;
    private Products productId;
    private String name;
    private double unitPrice;
    private int orderQuantity;

    public OrderDetails() {
    }

    public OrderDetails(Orders orderId, Products productId, String name, double unitPrice, int orderQuantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.orderQuantity = orderQuantity;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public void displayData() {

    }
}