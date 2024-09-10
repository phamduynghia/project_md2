package presentation.users;

import entity.*;
import feature.impl.*;
import presentation.run.MenuManagement;
import util.InputMethods;
import util.Messages;
import util.Validate;

import java.util.Comparator;

import static util.Colors.*;

public class MenuUserManagement {

    private final UserFeatureImpl userFeature = new UserFeatureImpl();
    private final ProductsFeatureImpl productsFeature = new ProductsFeatureImpl();
    private final CategoriesFeatureImpl categoriesFeature = new CategoriesFeatureImpl();
    private final OrderFeatureImpl orderFeature = new OrderFeatureImpl();
    private final OrderDetailFeatureImpl orderDetailFeature = new OrderDetailFeatureImpl();

    public MenuUserManagement() {
        boolean isExist = true;
        do {
            System.out.println();
            System.out.println(BLUE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ MENU NGƯỜI DÙNG ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┃         " + PURPLE + "1. Danh sách chi tiết sản phẩm theo mã sản phẩm                            " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "2. Danh sách chi tiết sản phẩm theo danh mục                               " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "3. Danh sách sản phẩm được bán                                             " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "4. Danh sách sản phẩm mời nhất                                             " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "5. Tìm kiếm sản phẩm theo tên hoặc mô tả sản phẩm                          " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "6. Thêm thông tin địa chỉ người dùng                                       " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "7. Thay đổi thông tin người dùng                                           " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "8. Sản phẩm yêu thích                                                      " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "9. Mua sản phẩm                                                            " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "10. Chi tiết đơn hàng                                                      " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "11. Đăng xuất                                                              " + BLUE + "┃");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println(Messages.CHOICE);
            int choice = InputMethods.getInteger();
            System.out.println();
            switch (choice) {
                case 1:
                    ListProductByProductId();
                    break;
                case 2:
                    ListProductByCategory();
                    break;
                case 3:
                    ListOfProductsSold();
                    break;
                case 4:
                    SortByNewestProduct();
                    break;
                case 5:
                    SearchByProductNameOrDescription();
                    break;
                case 6:
                    new AddressUserManagement();
                    break;
                case 7:
                    ChangeInformationUser();
                    break;
                case 8:
                    new WithListUserManagement();
                    break;
                case 9:
                    new ShoppingCartManagement();
                    break;
                case 10:
                    showOrderDetail();
                    break;
                case 11:
                    isExist = false;
                    break;
                default:
                    System.err.println(RED + "Vui lòng nhập lại từ 1 -> 11" + RESET);
            }
        } while (isExist);
    }

    public void ListProductByProductId() {
        System.out.println(BLUE + "Danh sách sản phẩm theo mã sản phẩm" + RESET);
        System.out.println();
        System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
        for (Products product : productsFeature.getAll()) {
            product.displayData();
        }
    }

    public void ListProductByCategory() {
        System.out.println(BLUE + "Danh sách sản phẩm theo danh mục" + RESET);
        System.out.println();
        System.out.println(BLUE + "Tất cả danh mục: " + RESET);
        System.out.println("+----+----------------------+----------------------------------------------+--------------------+");
        for (Category category : categoriesFeature.getAll()) {
            if (category.isStatus()){
                category.displayData();
            }
        }
        System.out.println();
        System.out.println(BLUE + "Vui lòng chon một danh mục: " + RESET);
        int selectedCategory = InputMethods.getInteger();
        Category category = categoriesFeature.findById(selectedCategory);
        if (category != null && category.isStatus()) {
            System.out.println(BLUE + "Danh mục đã chọn: " + category.getCategoryId() + RESET);
            System.out.println();
            System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
            for (Products product : productsFeature.getAll()) {
                if (product.getCategoryId().getCategoryId() == selectedCategory && product.getCategoryId().isStatus()) {
                    product.displayData();
                }
            }
        }
        else  {
            System.out.println(RED + "Danh mục không có sản phẩm nào hoặc không có danh mục." + RESET);
        }
    }

    public void ListOfProductsSold() {
        System.out.println(BLUE + "Danh sách sản phẩm được bán" + RESET);
        System.out.println();
        System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
        for (Products product : productsFeature.getAll()) {
            if(product.getStockQuantity() > 0) {
                product.displayData();
            }
        }
    }

    //Tìm kiếm sản phẩm theo tên hoặc mô tả sản phẩm
    public void SearchByProductNameOrDescription() {
        System.out.println(BLUE + "Mời bạn nhập vào từ khóa muốn tìm kiếm: " + RESET);
        String searchName = InputMethods.getString();
        boolean isExist = false;
        System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
        for (Products product : productsFeature.getAll()) {
            if (product.getProductName().toLowerCase().contains(searchName.toLowerCase()) || product.getDescription().toLowerCase().contains(searchName.toLowerCase())) {
                product.displayData();
                isExist = true;
            }
        }
        if (!isExist) {
            System.err.println(RED + "Không tìm thấy sản phẩm phù hợp. " + RESET);
        }
        else {
            System.out.println(GREEN + "Đã tìm thấy sản phẩm" + RESET);
        }
    }


    public void  SortByNewestProduct(){
        System.out.println(BLUE + "Danh sách sản phẩm mới nhất" + RESET);
        System.out.println();
        productsFeature.getAll().sort(Comparator.comparing(Products::getCreatedAt).reversed());
        System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
        System.out.println("| ID |   Tên sản phẩm   |              sku                     |        Mô tả sản phẩm            | Giá sản phẩm | Số lượng |       image      |  danh mục      |  Ngày tạo  |   Ngày cập nhật   |");
        System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
        for (Products product : productsFeature.getAll()) {
            product.displayData();
        }
    }

    public void ChangeInformationUser(){
        User newUser = new User();
        newUser.setId(MenuManagement.userLogin.getId());
        newUser.setRoles(MenuManagement.userLogin.getRoles());
        newUser.setStatus(MenuManagement.userLogin.isStatus());
        newUser.setPassword(MenuManagement.userLogin.getPassword());
        newUser.setAddress(MenuManagement.userLogin.getAddress());
        newUser.setEmail(MenuManagement.userLogin.getEmail());
        newUser.setCreatedAt(MenuManagement.userLogin.getCreatedAt());
        newUser.setUpdatedAt(MenuManagement.userLogin.getUpdatedAt());
        newUser.setPhone(MenuManagement.userLogin.getPhone());
        System.out.printf( BLUE + "\nNhập vào tên mới của bạn (Tên cũ: %s " + RESET + BLUE +"): " + RESET , CYAN + MenuManagement.userLogin.getFullName() + RESET);
        String name = InputMethods.getString();
        newUser.setFullName(name);
        MenuManagement.userLogin.setFullName(name);
        while (true) {
            boolean check = false;
            System.out.printf(BLUE + "\nNhập username (username cũ: %s" + RESET + BLUE +"): " + RESET , CYAN + MenuManagement.userLogin.getUserName()+ RESET);
            String username = InputMethods.getString();
            if (!Validate.usernames(username)) {
                System.err.println(RED + "Trống rỗng " + RESET);
                return;
            }

            for (User user : userFeature.getAll()) {
                if (user.getUserName().equals(username)) {
                    if (user.getUserName().equals(MenuManagement.userLogin.getUserName())) {
                        check = true;
                    }
                }
            }

            if (!check) {
                newUser.setUserName(username);
                userFeature.save(newUser);
                System.out.println();
                System.out.println(GREEN + "Đã đổi thành công thông tin của bạn" + RESET);
                System.out.println();
                break;
            }
            else {
                System.err.println(RED + "Tên đăng nhập đã bị trùng..." +RESET);
            }
        }
    }

    public void showOrderDetail() {
        User user = MenuManagement.userLogin;
        if (user == null) {
            System.err.println("Vui lòng đăng nhập để xem đơn hàng!");
            return;
        }
        if ( orderFeature.getAll().stream().filter(item-> item.getUserId() == user.getId()).count() <= 0) {
            System.out.println(Messages.IS_EMPTY);
            return;
        }
        System.out.println("+----+--------------------------------------+-----------------+---------------------+--------------------------------------+--------------+---------------+");
        System.out.println("| ID |        Tên khách hàng, Số seri       |    Tổng tiền    | Trạng thái đơn hàng |   Địa chỉ, Người nhận, Số điện thoại |   Ngày mua   |   Ngày nhập   |");
        System.out.println("+----+--------------------------------------+-----------------+---------------------+--------------------------------------+--------------+---------------+");
        for (Orders orders : orderFeature.getAll()) {
            if (orders.getUserId() == user.getId()) {
                orders.displayData();
            }
        }
    }
}