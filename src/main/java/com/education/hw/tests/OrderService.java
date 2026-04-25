package com.education.hw.tests;

import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        if (order == null) {
            return "Order cannot be null";
        }

        try {
            int orderId = orderRepository.saveOrder(order);
            if(orderId < 0) {
                return "Order processing failed";
            }
            return "Order processed successfully";
        } catch (Exception e) {
            return "Order processing failed";
        }
    }

    public double calculateTotal(int id) {
        Optional<Order> order = orderRepository.getOrderById(id);
        return order.orElseThrow(() -> new IllegalArgumentException("Order not found: " + id))
                .getTotalPrice();
    }
}
