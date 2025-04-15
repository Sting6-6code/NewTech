package Business.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDirectory {
    private List<Order> orderList;
    
    public OrderDirectory() {
        orderList = new ArrayList<>();
    }
    
    public void addOrder(Order order) {
        orderList.add(order);
    }
    
    public void removeOrder(Order order) {
        orderList.remove(order);
    }
    
    public List<Order> getOrderList() {
        return orderList;
    }
    
    public Order findOrderByRequestId(String requestId) {
        for (Order order : orderList) {
            if (order.getRequestId().equals(requestId)) {
                return order;
            }
        }
        return null;
    }
}
