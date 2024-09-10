package feature.impl;

import entity.OrderDetails;
import entity.Orders;
import feature.IOrderDetail;
import util.IOFiles;
import util.Messages;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailFeatureImpl implements IOrderDetail {

    public static List<OrderDetails> orderDetailsList = (List<OrderDetails>) IOFiles.readFromFile(IOFiles.ORDER_DETAIL_PATH);

    public OrderDetailFeatureImpl() {
        List<OrderDetails> orderDetails = (ArrayList<OrderDetails>) IOFiles.readFromFile(IOFiles.ORDER_DETAIL_PATH);
        if (orderDetails == null) {
            // Nếu không đọc được dữ liệu từ file, khởi tạo danh sách orderDetailsList trống
            orderDetails = new ArrayList<>();
        }
        orderDetailsList = orderDetails;
    }

    @Override
    public List<OrderDetails> getAll() {
        return orderDetailsList;
    }

    @Override
    public void save(OrderDetails element) {
        orderDetailsList.add(element);
        IOFiles.writeToFile(orderDetailsList, IOFiles.ORDER_DETAIL_PATH);
    }
}