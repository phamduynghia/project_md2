package presentation.users;

import entity.Category;
import entity.Products;
import feature.impl.CategoriesFeatureImpl;
import feature.impl.ProductsFeatureImpl;
import util.InputMethods;
import util.Messages;

import java.util.Comparator;

import static util.Colors.*;
import static util.Colors.RESET;

public class MenuHomeManagement {

    public ProductsFeatureImpl productsFeature = new ProductsFeatureImpl();
    public CategoriesFeatureImpl categoriesFeature = new CategoriesFeatureImpl();

    public MenuHomeManagement() {
        boolean isExist = true;
        do {
            System.out.println();
            System.out.println(BLUE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ TRANG CHỦ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┃         " + PURPLE + "1. Danh sách chi tiết sản phẩm theo mã sản phẩm                            " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "2. Danh sách chi tiết sản phẩm theo danh mục                               " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "3. Danh sách sản phẩm được bán                                             " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "4. Danh sách sản phẩm mời nhất                                             " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "5. Tìm kiếm sản phẩm theo tên hoặc mô tả sản phẩm                          " + BLUE + "┃");
            System.out.println("┃         " + PURPLE + "6. Đăng nhập                                                               " + BLUE + "┃");
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
                    isExist = false;
                    System.out.println(BLUE + "Mời bạn đăng nhập..." + RESET);
                    break;
                default:
                    System.err.println(RED + "Vui lòng nhập lại từ 1 -> 6" + RESET);
            }
        } while (isExist);
    }



    private void ListProductByProductId() {
        System.out.println(BLUE + "Danh sách sản phẩm theo mã sản phẩm" + RESET);
        System.out.println();
        System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
        for (Products product : productsFeature.getAll()) {
            product.displayData();
        }
    }

    private void ListProductByCategory() {
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

    private void ListOfProductsSold() {
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
    private void SearchByProductNameOrDescription() {
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

    private void  SortByNewestProduct(){
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
}