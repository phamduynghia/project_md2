package entity;

import util.InputMethods;

import java.io.Serializable;

import static util.Colors.*;

public class ShoppingCart implements Serializable {
    private int shoppingCartId;
    private Products productId;
    private User userId;
    private int orderQuantity;
    private double totalPrice;

    public ShoppingCart() {
    }

    public ShoppingCart(int shoppingCartId, Products productId, User userId, int orderQuantity) {
        this.shoppingCartId = shoppingCartId;
        this.productId = productId;
        this.userId = userId;
        this.orderQuantity = orderQuantity;
    }

    public int getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(int shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public void inputData(User user, Products product) {
        this.userId = user;
        this.productId = product;
        System.out.println(BLUE + "Mời bạn nhập vào số lượng muốn mua: " + RESET);
        this.orderQuantity = InputMethods.getIntegerQuantity(product);
    }

    public void displayData() {
//        System.out.println("+----+----------------+------------------+-------------------+");
//        System.out.println("| ID | Tên người dùng |   Tên sản phẩm   | Số lượng sản phẩm |");
//        System.out.println("+----+----------------+------------------+-------------------+");
        System.out.printf("| %-2d |   %-10s   |  %-14s  |       %-5d       |\n"
                ,this.shoppingCartId ,this.getUserId().getUserName(),this.getProductId().getProductName(), this.orderQuantity);
        System.out.println("+----+----------------+------------------+-------------------+");
    }
}