package presentation.admin;

import entity.Category;
import feature.impl.CategoriesFeatureImpl;
import util.InputMethods;
import util.Messages;

import java.util.Comparator;

import static util.Colors.*;

public class CategoryManagement {
    public CategoriesFeatureImpl categoriesFeature = new CategoriesFeatureImpl();

    public CategoryManagement() {
        boolean isExist = true;
        do {
            System.out.println();
            System.out.println(BLUE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ MANAGER CATEGORY ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┃            " + PURPLE + "1. Nhập số danh mục thêm mới và nhập thông tin cho từng danh mục        " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "2. Hiển thị thông tin tất cả các danh mục                               " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "3. Sửa tên danh mục theo mã danh mục                                    " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "4. Xóa danh muc theo mã danh mục (lưu ý ko xóa khi có sản phẩm)         " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "5. Tìm kiếm danh mục theo tên danh mục                                  " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "6. Sắp xếp danh mục theo thứ tự a -> z                                  " + BLUE + "┃");
            System.out.println("┃            " + PURPLE + "7. Quay lại                                                             " + BLUE + "┃");
            System.out.println("┃                                                                                    ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.println(Messages.CHOICE);
            int choice = InputMethods.getInteger();
            System.out.println();
            switch (choice) {
                case 1:
                    handleAddNewCategory();
                    break;
                case 2:
                    handleShowListCategory();
                    break;
                case 3:
                    handleEditCategory();
                    break;
                case 4:
                    handleDeleteCategory();
                    break;
                case 5:
                    handleSearchByCategory();
                    break;
                case 6:
                    handleSortByCategory();
                    break;
                case 7:
                    isExist = false;
                    break;
                default:
                    System.err.println(RED + "Vui lòng nhập lại từ 1 -> 7" + RESET);
            }
        } while (isExist);
    }

    private void handleAddNewCategory() {
        System.out.println(BLUE + "Mời bạn nhập vào số lượng danh mục: " + RESET);
        int countCategory = InputMethods.getInteger();
        for (int i = 0; i < countCategory; i++) {
            System.out.println(GREEN + "Danh mục thứ " + (i + 1) + RESET);
            Category category = new Category();
            category.setCategoryId(categoriesFeature.getNewId());
            category.inputData();
            categoriesFeature.save(category);
        }
        System.out.println(GREEN + "Đã thêm thành công " + countCategory + " danh mục" + RESET);
    }

    private void handleShowListCategory() {
        if (categoriesFeature.getAll().isEmpty()) {
            System.err.println(RED + "Chưa có danh mục nào" + RESET);
            return;
        }
        System.out.println("+----+----------------------+----------------------------------------------+--------------------+");
        System.out.println("| ID |     Tên danh mục     |               Mô tả danh mục                 |     Trạng thái     |");
        System.out.println("+----+----------------------+----------------------------------------------+--------------------+");
        for (Category category : categoriesFeature.getAll()) {
            category.displayData();
        }
    }

    private void handleEditCategory() {
        System.out.println(BLUE + "Bạn muốn sửa danh mục có mã là:: " + RESET);
        int indexCategory = InputMethods.getInteger();
        Category category = categoriesFeature.findById(indexCategory);
        if (category == null) {
            System.err.println(RED + "Không tồn tại danh mục" + RESET);
            return;
        }
        category.inputData();
        categoriesFeature.save(category);
        System.out.println(GREEN + "Đã thay đổi thành công danh mục " + indexCategory + RESET);
    }

    private void handleDeleteCategory() {
        System.out.println(BLUE + "Bạn muốn xóa danh mục có mã là: " + RESET);
        int indexCategory = InputMethods.getInteger();
        categoriesFeature.delete(indexCategory);
    }

    private void handleSearchByCategory() {
        System.out.println(BLUE + "Mời bạn nhập vào tên danh mục cần tìm kiếm: ");
        String categoryName = InputMethods.getString();
        int countCategory = 0;
        for (Category category : categoriesFeature.getAll()) {
            if (category.getCategoryName().equals(categoryName)) {
                category.displayData();
                countCategory++;
            }
        }
        if (countCategory == 0) {
            System.err.println(RED + "Không tìm thấy danh mục có tên là: " + categoryName);
        } else {
            System.out.println(GREEN + "Đã tìm thấy " + countCategory + " danh mục." + RESET);
        }
    }

    private void handleSortByCategory() {
        categoriesFeature.getAll().sort(Comparator.comparing(Category::getCategoryName));
        System.out.println("+----+----------------------+----------------------------------------------+--------------------+");
        System.out.println("| ID |     Tên danh mục     |               Mô tả danh mục                 |     Trạng thái     |");
        System.out.println("+----+----------------------+----------------------------------------------+--------------------+");
        for (Category category : categoriesFeature.getAll()) {
            category.displayData();
        }
        System.out.println(GREEN + "Đã sắp xếp thành công danh mục sản phẩm theo thứ tự từ a -> z" + RESET);
    }
}