package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFiles {
    public static final String USER_PATH = "src/database/accountUser.txt";
    public static final String CATEGORY_PATH = "src/database/category.txt";
    public static final String PRODUCT_PATH = "src/database/product.txt";
    public static final String ADDRESS_PATH = "src/database/address.txt";
    public static final String SHOPPING_CART_PATH = "src/database/shoppingCart.txt";
    public static final String Wish_List_PATH = "src/database/wishList.txt";
    public static final String Order_PATH = "src/database/order.txt";
    public static final String ORDER_DETAIL_PATH = "src/database/orderDetail.txt";

    public static void writeToFile(Object o, String path) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
        } catch (IOException ignored) {

        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Object readFromFile(String path) {
        Object o = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            o = ois.readObject();
        } catch (IOException ignored) {

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return o;
    }
}