package entity;

import constants.OrderStatus;
import constants.Payments;
import feature.impl.ShoppingCartFeatureImpl;
import util.InputMethods;
import util.Validate;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static util.Colors.BLUE;
import static util.Colors.RESET;

public class Orders implements Serializable {
    private int orderId;
    private String serialNumber;
    private User userId;
    private double totalPrice;
    private OrderStatus orderStatus;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private Date createAt;
    private Date receivedAt;
    private Payments payments;
    private List<ShoppingCart> shoppingCarts;

    public Orders() {
    }

    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public Orders(int orderId, String serialNumber, User userId, double totalPrice, OrderStatus orderStatus, String note, String receiveName, String receiveAddress, String receivePhone, Date createAt, Date receivedAt) {
        this.orderId = orderId;
        this.serialNumber = serialNumber;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.note = note;
        this.receiveName = receiveName;
        this.receiveAddress = receiveAddress;
        this.receivePhone = receivePhone;
        this.createAt = createAt;
        this.receivedAt = receivedAt;
        this.payments = payments;
        this.shoppingCarts = shoppingCarts;
    }

    public Payments getPayments() { return payments; }

    public void setPayments(Payments payments) { this.payments = payments; }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getUserId() {
       return this.userId.getId();
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

        public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }



    public void inputData(User user) {
        this.serialNumber = UUID.randomUUID().toString();
        this.userId = user;
        this.totalPrice = calculateTotalPrice(user);
        this.receiveAddress = user.getAddress();
        this.receivePhone = user.getPhone();
        this.receiveName = user.getFullName();
        this.orderStatus = OrderStatus.WAITING;
        System.out.println(BLUE + "Mời bạn nhập vào nội dung: " + RESET);
        this.note = InputMethods.getNode();
        this.createAt = new Date();
        this.receivedAt = Validate.inputReceiveAt(this.createAt);
    }

    public double calculateTotalPrice(User user) {
        if (user == null) {
            System.err.println("Bạn phải đăng nhâp");
            return 0.0;
        }
        else {
            List<ShoppingCart> shoppingCartsList = ShoppingCartFeatureImpl.shoppingCartsList.stream().filter(item -> item.getUserId().getId() ==user.getId()).toList();
            double totalPrice = 0;
            for (ShoppingCart shoppingCart : shoppingCartsList) {
                totalPrice += shoppingCart.getOrderQuantity() * shoppingCart.getProductId().getUnitPrice();
            }
            return totalPrice;
        }
    }

    public void displayData() {
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

//        System.out.println("+----+--------------------------------------+-----------------+---------------------+--------------------------------------+--------------+---------------+");
//        System.out.println("| ID |        Tên khách hàng, Số seri       |    Tổng tiền    | Trạng thái đơn hàng |   Địa chỉ, Người nhận, Số điện thoại |   Ngày mua   |   Ngày nhân   |");
//        System.out.println("+----+--------------------------------------+-----------------+---------------------+--------------------------------------+--------------+---------------+");
        String formPrintFirst = "| %-3d| %-34s   |  %-13s  |   %-15s   |    %-31s   | %-12s |  %-11s  |\n";
        String formPrintNext =  "|    | %-32s |                 |                     |    %-31s   |              |               |\n";
        String formPrintLast =  "|    |                                      |                 |                     |    %-31s   |              |               |\n";
        System.out.printf(formPrintFirst, this.orderId, this.userId.getUserName(), vndFormat.format(this.totalPrice ), this.orderStatus, this.receiveName, sdf.format(this.createAt), sdf.format(this.receivedAt));
        System.out.printf(formPrintNext, this.serialNumber, this.receiveAddress);
        System.out.printf(formPrintLast, this.receivePhone);
        System.out.println("+----+--------------------------------------+-----------------+---------------------+--------------------------------------+--------------+---------------+");
    }
}
