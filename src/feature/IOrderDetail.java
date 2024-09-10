package feature;

import entity.OrderDetails;

import java.util.List;

public interface IOrderDetail  {
    List<OrderDetails> getAll();

    void save(OrderDetails element);
}