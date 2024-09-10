package entity;

import feature.impl.CategoriesFeatureImpl;
import util.InputMethods;
import util.Messages;

import java.io.Serializable;

import static util.Colors.*;

public class Category implements Serializable {
    private int categoryId;
    private String categoryName;
    private String description;
    private boolean status;

    public Category() {
    }

    public Category(int categoryId, String categoryName, String description, boolean status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void inputData() {
        System.out.println("Mời bạn nhập vào tên danh mục: ");
//        this.categoryName = InputMethods.getString();
        this.categoryName = inputCateName();
        System.out.println("Mời bạn nhập vào mô tả danh mục: ");
        this.description = InputMethods.getString();
        System.out.println("Mời bạn nhập vào trạng thái danh mục: ");
        this.status = InputMethods.getBoolean();
    }

    public String inputCateName () {
        do{
            String cateName = InputMethods.getString();
            if (cateName.trim().isEmpty()) {
                System.err.println(Messages.IS_EMPTY);
            }
            boolean isExist = false;
            for (Category category : CategoriesFeatureImpl.categoryList) {
                if (category.getCategoryName().equals(cateName)) {
                    isExist = true;
                    break;
                }
            }

            if (isExist) {
                System.err.println(RED + "Tên danh mục đã tồn tại, vui lòng nhập lại." + RESET);
            } else {
                return cateName;
            }
        } while (true);
    }


    public void displayData() {
//        System.out.println("+----+--------------+----------------------------------------------+--------------------+");
//        System.out.println("| ID | Tên danh mục |               Mô tả danh mục                 |     Trạng thái     |");
//        System.out.println("+----+--------------+----------------------------------------------+--------------------+");
        System.out.printf("| %-2d | %-16s     | %-36s         | %-16s   |\n"
                ,this.categoryId,this.categoryName, this.description,this.status  ? "Hiển thị" : "Không hiển thị");
        System.out.println("+----+----------------------+----------------------------------------------+--------------------+");
    }
}
