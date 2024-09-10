package entity;

import constants.Roles;
import util.InputMethods;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import static util.Colors.*;

public class User implements Serializable {
    private int Id, wallet;
    private String fullName, userName, password, email, phone, address, avatar;
    private Date createdAt, updatedAt;
    private Roles roles;
    private boolean status;

    public User() {
    }

    public User(int Id, String fullName, String userName, String password, String email, String phone, String address, String avatar, Date createdAt, Date updatedAt, Roles roles, boolean status) {
        this.Id = Id;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roles = roles;
        this.status = status;
        this.wallet = wallet;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void inputData() {
        System.out.println(BLUE + "Mời nhập vào tên của bạn: " + RESET);
        this.fullName = InputMethods.getString();
        System.out.println(BLUE + "Mời nhập vào địa chỉ của bạn: " + RESET);
        this.address = InputMethods.getString();
        System.out.println(BLUE + "Mời nhập vào số điện thoại của bạn: " + RESET);
        this.phone = InputMethods.getPhone();
        System.out.println(BLUE + "Mời nhập vào tên đăng nhập của bạn (Không được có dấu cách): " + RESET);
        this.userName = InputMethods.getString();
        System.out.println(BLUE + "Mời nhập vào email ( example@gmail.com): " + RESET);
        this.email = InputMethods.getMail();
        System.out.print(BLUE + "Mời nhập vào mật khẩu của bạn (Không được có dấu cách và ít nhất 8 ký tự gồm chữ in,chữ thường,số và kí tự đặc biệt): " + RESET);
        this.password = InputMethods.getString();
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.status = true;
        this.roles = Roles.ROLE_USER;
    }

    public void displayData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.printf("| %-2d |    %-8s   | %-14s  |     %-21s       | %-30s |  %-10s   |   %-8s | %-10s |\n"
                , this.getId(), this.getUserName(), this.getRoles(), this.getEmail(), this.getAddress(), this.getPhone()
                , this.status ? "UnBlock" : "Block", sdf.format(this.getCreatedAt()));

        System.out.println("+----+---------------+-----------------+---------------------------------+--------------------------------+---------------+------------+------------+");
    }

}

