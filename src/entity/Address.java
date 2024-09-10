package entity;

import util.InputMethods;

import java.io.Serializable;
import java.util.List;

public class Address implements Serializable {
    private int addressId;
    private User userId;
    private String fullAddress;
    private String phone;
    private String receiveName;

    public Address() {
    }

    public Address(int addressId, User userId, String fullAddress, String phone, String receiveName) {
        this.addressId = addressId;
        this.userId = userId;
        this.fullAddress = fullAddress;
        this.phone = phone;
        this.receiveName = receiveName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public void inputData(User user) {
        this.userId = user;
        System.out.println("Mời bạn nhập vào địa chi: ");
        this.fullAddress = InputMethods.getString();
        System.out.println("Mời bạn nhập vào số điện thoại");
        this.phone = InputMethods.getPhone();
        System.out.println("Mời nhập vào tên người nhận");
        this.receiveName = InputMethods.getString();
    }


    public void displayData() {
//        System.out.println("+----+---------------+----------------------------------------------+---------------+--------------------+");
//        System.out.println("| ID | ID_user       |                    Địa chỉ                   | Số điện thoại |   Tên người nhận   |");
//        System.out.println("+----+---------------+----------------------------------------------+---------------+--------------------+");
        System.out.printf("| %-2d | %-10s    | %-36s         |   %-10s  | %-18s |\n"
                , this.addressId, this.userId.getUserName(), this.fullAddress, this.phone, this.receiveName );
        System.out.println("+----+---------------+----------------------------------------------+---------------+--------------------+");
    }

}