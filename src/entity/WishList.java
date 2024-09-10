package entity;

import java.io.Serializable;

public class WishList implements Serializable  {
    private int wishListId;
    private User userId;
    private Products productId;

    public WishList() {
    }

    public WishList(int wishListId, User userId, Products productId) {
        this.wishListId = wishListId;
        this.userId = userId;
        this.productId = productId;
    }

    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    public void inputData(User user, Products product) {
        this.userId = user;
        this.productId = product;
    }

    public void displayData() {
//        System.out.println("+----+---------------------+--------------------+");
//        System.out.println("| ID |    Tên Người dùng   |    Tên sản phẩm    |");
//        System.out.println("+----+---------------------+--------------------+");
        System.out.printf("| %-2d |   %-15s   |  %-16s  |\n"
                ,this.wishListId, this.userId.getUserName(), this.productId.getProductName());
        System.out.println("+----+---------------------+--------------------+");

    }

}