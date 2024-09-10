package feature.impl;

import entity.Products;
import feature.IProduct;
import util.IOFiles;
import util.Messages;

import java.util.ArrayList;
import java.util.List;

public class ProductsFeatureImpl implements IProduct {

    public static List<Products> productList = (List<Products>) IOFiles.readFromFile(IOFiles.PRODUCT_PATH);

    public ProductsFeatureImpl() {
        List<Products> products = (List<Products>) IOFiles.readFromFile(IOFiles.PRODUCT_PATH);
        if (products == null) {
            // Nếu không đọc được dữ liệu từ file, khởi tạo danh sách productList trống
            products = new ArrayList<>();
        }
        productList = products;
    }

    @Override
    public List<Products> getAll() {
        return productList;
    }

    @Override
    public void save(Products element) {
        if (findById(element.getProductId()) == null) {
            productList.add(element);
        }
        else {
            productList.set(productList.indexOf(findById(element.getProductId())), element);
        }
        IOFiles.writeToFile(productList, IOFiles.PRODUCT_PATH);
    }

    @Override
    public void delete(Integer id) {
        if (findById(id) == null) {
            System.out.println(Messages.NOT_FOUND);
        }
        productList.remove(findById(id));
        IOFiles.writeToFile(productList, IOFiles.PRODUCT_PATH);
    }

    @Override
    public Products findById(Integer id) {
        for (Products product : productList) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public Integer getNewId() {
        int maxId = 0;
        for (Products product : productList) {
            if (product.getProductId() > maxId) {
                maxId = product.getProductId();
            }
        }
        return maxId + 1;
    }
}