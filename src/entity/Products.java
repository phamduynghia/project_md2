package entity;

import util.InputMethods;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static util.Colors.*;


public class Products implements Serializable {
    private int productId;
    private String sku;
    private String productName;
    private String description;
    private double unitPrice;
    private int stockQuantity;
    private String image;
    private Category categoryId;
    private Date createdAt;
    private Date updatedAt;
    private int selled;

    public Products() {
    }

    public Products(int productId, int selled,String sku, String productName, String description, double unitPrice, int stockQuantity, String image, Category categoryId, Date createdAt, Date updatedAt) {
        this.productId = productId;
        this.sku = sku;
        this.productName = productName;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
        this.image = image;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.selled = selled;
    }

    public int getSelled() {
        return selled;
    }

    public void setSelled(int selled) {
        this.selled = selled;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
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

    public void inputData(List<Category> list) {
        if( InputMethods.checkExistedCategory(list)) {
            this.sku = UUID.randomUUID().toString();
            System.out.println(BLUE + "Mời bạn nhập vào tên sản phẩm: " + RESET);
            this.productName = InputMethods.getString();
            System.out.println(BLUE + "Mời bạn nhập vào mô tả sản phẩm: " + RESET);
            this.description = InputMethods.getString();
            System.out.println(BLUE + "Mời bạn nhập vào giá của sản phẩm: " + RESET);
            this.unitPrice = InputMethods.getDoublePrice();
            System.out.println(BLUE + "Mời bạn nhập vào số lượng sản phẩm trong kho: " + RESET);
            this.stockQuantity = InputMethods.getIntegerStock();
            System.out.println(BLUE + "Mời nhập vào hình ảnh sản phẩm: " + RESET);
            this.image = InputMethods.getString();
            System.out.println("+----+----------------------+----------------------------------------------+--------------------+");
            for (Category category : list) {
                if (category.isStatus()) {
                    category.displayData();
                }
            }
            while (true) {
                boolean isExist = false;
                System.out.println(BLUE + "Mời bạn chon mã danh mục: " + RESET);
                int idCategory = InputMethods.getInteger();
                for (Category category : list) {
                    if (category.getCategoryId() == idCategory) {
                        if (category.isStatus()) {
                            this.categoryId = category;
                            isExist = true;
                            break;
                        }
                        else {
                            System.err.println(RED + "Không thể thêm danh mục này vì trạng thái là false." + RESET);
                            break;
                        }
                    }
                }
                if (isExist ) {
                    break;
                } else{
                    System.err.println(RED + "Không tồn tại danh mục, vui lòng nhâp lại..." + RESET);
                }
            }
            this.createdAt = new Date();
            this.updatedAt = new Date();
        }
        else{
            System.out.println(BLUE + "Bạn phải nhập danh mục trước" +RESET);
        }
    }

    public void displayData() {
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.printf("| %-2d | %-16s | %-12s | %-32s | %-12s | %-3d      | %-15s  | %-14s | %-10s |   %-12s    |\n"
                ,this.productId, this.productName, this.sku, this.description, vndFormat.format(this.unitPrice), this.stockQuantity, this.image, this.categoryId.getCategoryName(), sdf.format(this.createdAt), sdf.format(this.updatedAt));
        System.out.println("+----+------------------+--------------------------------------+----------------------------------+--------------+----------+------------------+----------------+------------+-------------------+");
    }
}