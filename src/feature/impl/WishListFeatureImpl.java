package feature.impl;

import entity.WishList;
import feature.IWish_List;
import util.IOFiles;
import util.Messages;

import java.util.ArrayList;
import java.util.List;

public class WishListFeatureImpl implements IWish_List {
    public static List<WishList> wishListList =  (List<WishList>) IOFiles.readFromFile(IOFiles.Wish_List_PATH);

    public WishListFeatureImpl() {
        List<WishList> wishLists = (List<WishList>) IOFiles.readFromFile(IOFiles.Wish_List_PATH);
        if (wishLists == null) {
            // Nếu không đọc được dữ liệu từ file, khởi tạo danh sách wishListList trống
            wishLists = new ArrayList<>();
        }
        wishListList = wishLists;
    }

    @Override
    public List<WishList> getAll() {
        return wishListList;
    }

    @Override
    public void save(WishList element) {
        if (findById(element.getWishListId()) == null) {
            element.setWishListId(getNewId());
            wishListList.add(element);
        }
        else {
            wishListList.set(wishListList.indexOf(findById(element.getWishListId())), element);
        }
        IOFiles.writeToFile(wishListList, IOFiles.Wish_List_PATH);
    }

    @Override
    public void delete(Integer id) {
        if (findById(id) == null) {
            System.out.println(Messages.NOT_FOUND);
        }
        wishListList.remove(findById(id));
        IOFiles.writeToFile(wishListList, IOFiles.Wish_List_PATH);
    }

    @Override
    public WishList findById(Integer id) {
        for (WishList wishList : wishListList) {
            if (wishList.getWishListId() == id) {
                return wishList;
            }
        }
        return null;
    }

    @Override
    public Integer getNewId() {
        int maxId = 0;
        for (WishList wishList : wishListList) {
            if (wishList.getWishListId() > maxId) {
                maxId = wishList.getWishListId();
            }
        }
        return maxId + 1;
    }
}