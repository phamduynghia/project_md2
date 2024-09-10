package presentation.admin;

import constants.OrderStatus;
import constants.Payments;
import entity.Orders;
import entity.Products;
import entity.ShoppingCart;
import entity.User;
import feature.IOrder;
import feature.IProduct;
import feature.IUser;
import feature.impl.OrderFeatureImpl;
import feature.impl.ProductsFeatureImpl;
import feature.impl.UserFeatureImpl;
import presentation.run.MenuManagement;
import util.Colors;
import util.InputMethods;
import util.Messages;

import java.util.List;

import static util.Colors.*;
import static util.Colors.RESET;

public class OrderManagement {
    private static final IOrder orderList = new OrderFeatureImpl();
    private static final IProduct productList = new ProductsFeatureImpl();
    private static final IUser userList = new UserFeatureImpl();

    public OrderManagement() {
        boolean isExist = true;
        do {
            System.out.println();
            System.out.println(BLUE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ORDER MANAGEMENT ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┃            " + PURPLE + "1.      Hiển thị tất cả đơn hàng                                        " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "2.      Tìm kiếm theo mã đơn hàng                                       " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "3.      Tìm kiếm theo trạng thái đơn hàng                               " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "4.      Xem chi tiết đơn hàng theo mã đơn hàng                          " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "5.      Thay đổi trạng thái đơn hàng                                    " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "6.      Quay lại                                                        " + BLUE + "┃");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println(Messages.CHOICE);
            int choice = InputMethods.getInteger();
            System.out.println();
            switch (choice) {
                case 1:
                    showAllOrder(true);
                    break;
                case 2:
                    SearchOrderId();
                    break;
                case 3:
                    SearchOrderStatus();
                    break;
                case 4:
                    DetailsOrderId(true);
                    break;
                case 5:
                    ChangeOrderStatus();
                    break;
                case 6:
                    return;
                default:
                    System.err.println(RED + "Vui lòng nhập lại từ 1 -> 6" + RESET);
            }
        } while (isExist);
    }

    private static void ChangeOrderStatus() {
        System.out.println("Nhập ID đơn hàng bạn muốn thay đổi trạng thái: ");
        int orderId = InputMethods.getInteger();
        if (orderList.findById(orderId) != null) {
            System.out.println(Colors.CYAN + "*********** DANH SÁCH TRẠNG THÁI ***********" + Colors.RESET);
            OrderStatus[] statuses = OrderStatus.values();
            int count = 1;
            for (OrderStatus status : statuses) {
                System.out.println(count + ". " + status);
                count++;
            }
            OrderStatus inputStatus;
            while (true) {
                try {
                    System.out.println(Colors.CYAN + "Nhập trạng thái bạn muốn thay đổi " + Colors.RESET);
                    inputStatus = OrderStatus.valueOf(InputMethods.getString());
                    break;
                } catch (Exception e) {
                    System.err.println("Trạng thái không hợp lệ, vui lòng thử lại!");
                }
            }
            updateQuantityProduct(orderList.findById(orderId), inputStatus);
            orderList.findById(orderId).setOrderStatus(inputStatus);
            orderList.save(orderList.findById(orderId));
            System.out.println(Colors.GREEN + "Trạng thái đơn hàng đã thay đổi thành " + inputStatus + Colors.RESET);
        } else {
            System.err.println("Không tìm thấy đơn hàng");
        }
    }

    private static void updateQuantityProduct(Orders byId, OrderStatus inputStatus) {
        if (byId.getOrderStatus() != OrderStatus.SUCCESS && inputStatus == OrderStatus.SUCCESS) {
            List<ShoppingCart> shoppingCarts = byId.getShoppingCarts();
            for (ShoppingCart shoppingCart : shoppingCarts) {
                if (productList.getAll().stream().anyMatch(p -> p.getProductId() == shoppingCart.getProductId().getProductId())) {
                    Products newProducts = productList.findById(shoppingCart.getProductId().getProductId());
                    newProducts.setStockQuantity(newProducts.getStockQuantity() - shoppingCart.getOrderQuantity());
                    newProducts.setSelled(newProducts.getSelled() + shoppingCart.getOrderQuantity());
                    productList.save(newProducts);
                }
            }
        } else if (byId.getOrderStatus() == OrderStatus.SUCCESS && inputStatus != OrderStatus.SUCCESS && byId.getPayments() == Payments.ONLINE) {
            List<ShoppingCart> shoppingCarts = byId.getShoppingCarts();
            for (ShoppingCart shoppingCart : shoppingCarts) {
                if (productList.getAll().stream().anyMatch(p -> p.getProductId() == shoppingCart.getProductId().getProductId())) {
                    Products newProducts = productList.findById(shoppingCart.getProductId().getProductId());
                    newProducts.setStockQuantity(newProducts.getStockQuantity() - shoppingCart.getOrderQuantity());
                    newProducts.setSelled(newProducts.getSelled() - shoppingCart.getOrderQuantity());
                    productList.save(newProducts);
                    User user = userList.findById(byId.getUserId());
                    user.setWallet((int) (user.getWallet() + byId.getTotalPrice()));
                    userList.save(user);
                }
            }
        } else if (byId.getOrderStatus() == OrderStatus.SUCCESS && inputStatus != OrderStatus.SUCCESS) {
            List<ShoppingCart> shoppingCarts = byId.getShoppingCarts();
            for (ShoppingCart shoppingCart : shoppingCarts) {
                if (productList.getAll().stream().anyMatch(p -> p.getProductId() == shoppingCart.getProductId().getProductId())) {
                    Products newProducts = productList.findById(shoppingCart.getProductId().getProductId());
                    newProducts.setStockQuantity(newProducts.getStockQuantity() - shoppingCart.getOrderQuantity());
                    newProducts.setSelled(newProducts.getSelled() - shoppingCart.getOrderQuantity());
                    productList.save(newProducts);
                }
            }

        }
    }


    public static void DetailsOrderId(boolean isAdmin) {
        if (isAdmin) {
            if (orderList.getAll().isEmpty()) {
                System.err.println("Danh sách đơn hàng đang trống");
                return;
            }
        } else {
            if (orderList.getAll().stream().noneMatch(o -> o.getUserId() == MenuManagement.userLogin.getId())) {
                System.err.println("Danh sách đơn hàng đang trống");
                return;
            }
        }
        System.out.println(Colors.CYAN + "Nhập ID đơn hàng bạn muốn hiển thị chi tiết đơn hàng" + Colors.RESET);
        int orderId = InputMethods.getInteger();
        if (isAdmin) {
            if (orderList.findById(orderId) != null) {
                orderList.findById(orderId).displayData();
            } else {
                System.err.println("Không tìm thấy đơn hàng");
            }
        } else {
            if (orderList.findById(orderId) != null) {
                orderList.findById(orderId);
            }
            System.err.println("Không tìm thấy đơn hàng");
        }
    }


    private static void SearchOrderStatus() {
    }

    private static void SearchOrderId() {
        if (orderList.getAll().isEmpty()) {
            System.out.println(Messages.IS_EMPTY);
            return;
        }
        System.out.println(Colors.CYAN + "Nhập ID đơn hàng bạn muốn hiển thị chi tiết đơn hàng: " + Colors.RESET);
        int orderId = InputMethods.getInteger();
        if (orderList.findById(orderId) != null) {
            orderList.findById(orderId).displayData();
        } else {
            System.out.println(Messages.NOT_FOUND);
        }
    }


    public static void showAllOrder(boolean isAdmin) {
        if (!isAdmin) {
            if (orderList.getAll().stream().noneMatch(o -> o.getOrderId() == MenuManagement.userLogin.getId())) {
                System.err.println("Danh sách đơn hàng đang trống");
                return;
            }
        } else {
            if (orderList.getAll().isEmpty()) {
                System.err.println("Danh sách đơn hàng đang trống");
                return;
            }
        }
        System.out.println(Colors.CYAN + "*************** Lịch sử đơn hàng ***************" + Colors.RESET);
        System.out.printf(Colors.GREEN + "%3s | %15s | %15s | %15s | %7s | %10s | %10s | %10s \n"
                , "ID", "ĐỊA CHỈ", "NHẬN ĐIỆN THOẠI", "GIÁ", "THANH TOÁN", "ĐẶT HÀNG TẠI", "NHẬN TẠI", "TRẠNG THÁI" + Colors.RESET);
        if (isAdmin) {
            orderList.getAll().forEach(Orders::displayData);
        } else {
            orderList.getAll().stream().filter(o -> o.getOrderId() == MenuManagement.userLogin.getId()).forEach(Orders::displayData);
        }
    }
}



