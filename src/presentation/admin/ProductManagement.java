package presentation.admin;

import entity.Products;
import feature.impl.CategoriesFeatureImpl;
import feature.impl.ProductsFeatureImpl;
import util.InputMethods;
import util.Messages;

import static util.Colors.*;

public class ProductManagement {
    private final ProductsFeatureImpl productsFeature = new ProductsFeatureImpl();
    private final CategoriesFeatureImpl categoriesFeature = new CategoriesFeatureImpl();

    public ProductManagement() {
        boolean isExist = true;
        do {
            System.out.println();
            System.out.println(BLUE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ MANAGER PRODUCTS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┃            " + PURPLE + "1. Nhập số sản phẩm thêm mới và nhập thông tin cho từng sản phẩm        " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "2. Hiển thị thông tin tất cả các sản phẩm                               " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "3. Sửa tên sản phẩm theo mã sản phẩm                                    " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "4. Xóa danh muc theo mã sản phẩm (lưu ý ko xóa khi có sản phẩm)         " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "5. Tìm kiếm sản phẩm theo tên sản phẩm                                  " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "6. Quay lại                                                             " + BLUE + "┃");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println(Messages.CHOICE);
            int choice = InputMethods.getInteger();
            System.out.println();
            switch (choice) {
                case 1:
                    handleAddNewProduct();
                    break;
                case 2:
                    handleShowListProduct();
                    break;
                case 3:
                    handleEditProduct();
                    break;
                case 4:
                    handleDeleteProduct();
                    break;
                case 5:
                    handleSearchByProduct();
                    break;
                case 6:
                    isExist = false;
                    break;
                default:
                    System.err.println("Vui lòng nhập lại từ 1 -> 6");
            }
        } while (isExist);
    }

    private void handleAddNewProduct() {
        System.out.println("Mời bạn nhập vào số lượng sản phẩm: ");
        int countProduct = InputMethods.getInteger();
        for (int i = 0; i < countProduct; i++) {
            System.out.println(GREEN + "Sản phẩm thứ " + (i + 1) + RESET);
            Products product = new Products();
            product.setProductId(productsFeature.getNewId());
            product.inputData(categoriesFeature.getAll());
            productsFeature.save(product);
        }
    }

    private void handleShowListProduct() {
        if (productsFeature.getAll().isEmpty()) {
            System.err.println(RED + "Không có sản phẩm nào" + RESET);
            return;
        }
        System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
        System.out.println("| ID |   Tên sản phẩm   |              sku                     |        Mô tả sản phẩm            | Giá sản phẩm | Số lượng |       image      |  danh mục      |  Ngày tạo  |   Ngày cập nhật   |");
        System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
        for (Products product : productsFeature.getAll()) {
            product.displayData();
        }
    }

    private void handleEditProduct() {
        System.out.println(BLUE + "Bạn muốn sửa sản phẩm có mã là: "+ RESET);
        int indexProduct = InputMethods.getInteger();
        Products product = productsFeature.findById(indexProduct);
        if (product == null) {
            System.err.println(RED + "Không tồn tại sản phẩm này" + RESET);
            return;
        }
        product.inputData(categoriesFeature.getAll());
        productsFeature.save(product);
        System.out.println(GREEN + "Đã thay đổi thành công sản phẩm "  + indexProduct + RESET);
    }

    private void handleDeleteProduct() {
        System.out.println(BLUE + "Bạn muốn xóa sản phẩm có mã là: " + RESET);
        int indexProduct = InputMethods.getInteger();
        productsFeature.delete(indexProduct);
    }

    private void handleSearchByProduct() {
        System.out.println(BLUE + "Mời bạn nhập vào tên sản phẩm cần tìm kiếm: " + RESET);
        String productName = InputMethods.getString();
        int countProduct = 0;
        for (Products product : productsFeature.getAll()) {
            if (product.getProductName().equals(productName)) {
                product.displayData();
                countProduct++;
            }
        }
        if (countProduct == 0) {
            System.err.println(RED + "Không tìm thấy sản phẩm có tên là: " + countProduct + RESET);
        } else {
            System.out.println(GREEN + "Đã tìm thấy " + countProduct + " sản phẩm." + RESET);
        }
    }
}