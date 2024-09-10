package feature.impl;

import entity.Orders;
import entity.ShoppingCart;
import feature.IOrder;
import util.IOFiles;
import util.Messages;

import java.util.ArrayList;
import java.util.List;

import static util.Colors.*;

public class OrderFeatureImpl implements IOrder {

    public static List<Orders> ordersList = (List<Orders>) IOFiles.readFromFile(IOFiles.Order_PATH);

    public OrderFeatureImpl() {
        List<Orders> orders = (ArrayList<Orders>) IOFiles.readFromFile(IOFiles.Order_PATH);
        if (orders == null) {
            // Nếu không đọc được dữ liệu từ file, khởi tạo danh sách orderList trống
            orders = new ArrayList<>();
        }
        ordersList = orders;
    }


    @Override
    public List<Orders> getAll() {
        return ordersList;
    }

    @Override
    public void save(Orders element) {
        if (findById(element.getOrderId()) == null) {
            element.setOrderId(getNewId());
            ordersList.add(element);
        }
        else {
            ordersList.set(ordersList.indexOf(findById(element.getOrderId())), element);
        }
        IOFiles.writeToFile(ordersList, IOFiles.Order_PATH);
    }

    @Override
    public void delete(Integer id) {
        if (findById(id) == null) {
            System.out.println(Messages.NOT_FOUND);
        }
        ordersList.remove(findById(id));
        IOFiles.writeToFile(ordersList, IOFiles.Order_PATH);
    }

    @Override
    public Orders findById(Integer id) {
        for (Orders order : ordersList) {
            if (order.getOrderId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public Integer getNewId() {
        int maxId = 0;
        for (Orders order : ordersList) {
            if (order.getOrderId() > maxId) {
                maxId = order.getOrderId();
            }
        }
        return maxId + 1;
    }
}