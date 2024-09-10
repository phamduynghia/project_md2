package presentation.users;

import entity.*;
import feature.impl.ProductsFeatureImpl;
import feature.impl.UserFeatureImpl;
import feature.impl.WishListFeatureImpl;
import presentation.run.MenuManagement;
import util.InputMethods;
import util.Messages;

import static util.Colors.*;

public class WithListUserManagement {

    public UserFeatureImpl userFeature = new UserFeatureImpl();
    public ProductsFeatureImpl productsFeature = new ProductsFeatureImpl();
    public WishListFeatureImpl wishListFeature = new WishListFeatureImpl();

    public WithListUserManagement() {
        boolean isExist = true;
        do {
            System.out.println();
            System.out.println(BLUE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ SẢN PHẨM YÊU THÍCH ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┃         " + PURPLE + "1. Hiển thị danh sách sản phẩm yêu thích                                   " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "2. Thêm sản phẩm yêu thích                                                 " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "3. Xóa sản phẩm yêu thích                                                  " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "4. Quay lại                                                                " + BLUE + "┃");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println(Messages.CHOICE);
            int choice = InputMethods.getInteger();
            System.out.println();
            switch (choice) {
                case 1:
                    showAllWishListProduct();
                    break;
                case 2:
                    addWishListProduct();
                    break;
                case 3:
                    deleteWishListProduct();
                    break;
                case 4:
                    isExist = false;
                    break;
                default:
                    System.err.println(RED + "Vui lòng nhập lại từ 1 -> 4" + RESET);
            }
        } while (isExist);
    }

    public void showAllWishListProduct() {
        User user = MenuManagement.userLogin;
        if (user == null || wishListFeature.getAll().stream().noneMatch(item -> item.getUserId().getId() == user.getId())) {
            System.err.println(RED + "Không có sản phẩm yêu thích " + RESET);
            return;
        }
        System.out.println("+----+---------------------+--------------------+");
        System.out.println("| ID |    Tên Người dùng   |    Tên sản phẩm    |");
        System.out.println("+----+---------------------+--------------------+");
        for (WishList wishList : wishListFeature.getAll()) {
            if (wishList.getUserId().getId() == user.getId()) {
                wishList.displayData();
            }
        }
        System.out.println();
    }

    public void addWishListProduct() {
        User user = MenuManagement.userLogin;

        if (user == null) {
            System.err.println(RED + "Không có người dùng đăng nhập" + RESET);
            return;
        }
        System.out.println(BLUE + "Tất cả các sản phẩm: " + RESET);
        System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
        System.out.println("| ID |   Tên sản phẩm   |              sku                     |        Mô tả sản phẩm            | Giá sản phẩm | Số lượng |       image      |  danh mục      |  Ngày tạo  |   Ngày cập nhật   |");
        System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
        for (Products product : productsFeature.getAll()) {
            product.displayData();
        }
        System.out.println();
        System.out.println(BLUE + "Mời bạn thêm sản phẩm yêu thích: " + RESET);
        boolean isExist = true;
        do {
            System.out.print("Nhập ID sản phẩm cần thêm vào danh sách yêu thích: ");
            int idProduct = InputMethods.getInteger();
            Products selectedProduct = productsFeature.findById(idProduct);
            if (selectedProduct == null) {
                System.err.println(RED + "Sản phẩm không tồn tại, vui lòng nhập lại" + RESET);
            } else {
                if (wishListFeature.getAll().stream().anyMatch(item -> item.getUserId().getId() == user.getId()
                        && item.getProductId().getProductId() == idProduct)) {
                    System.err.println(RED + "Sản phẩm đã có trong danh sách yêu thích của bạn." + RESET);
                } else {
                    WishList wishListItem = new WishList();
                    wishListItem.inputData(user,selectedProduct);
                    wishListFeature.save(wishListItem);
                    isExist = false;
                    System.out.println(GREEN + "Đã thêm sản phẩm vào danh sách yêu thích." + RESET);
                }
            }
        }
        while (isExist) ;
    }

    public void deleteWishListProduct() {
        User user = MenuManagement.userLogin;
        System.out.println(BLUE + "Mời bạn nhập vào mã sản phẩm yêu thích: " + RESET);
        int idWishList = InputMethods.getInteger();
        boolean isExist = false;
        for(WishList wishList :wishListFeature.getAll()){
            if (wishList.getWishListId() == idWishList && wishList.getUserId().getId() == user.getId()){
                wishListFeature.delete(idWishList);
                isExist = true;
                break;
            }
        }
        if(isExist) {
            System.out.println(GREEN + "Đã xóa thành công sản phẩm yêu thích: " + RESET);
        } else {
            System.err.println(RED + "Không tìm thấy thông tin sản phẩm yêu thích của bạn." + RESET);
        }
    }
}