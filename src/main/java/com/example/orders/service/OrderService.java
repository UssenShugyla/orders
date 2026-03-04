package com.example.orders.service;

import com.example.orders.model.Order;
import com.example.orders.model.OrderStatus;
import com.example.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order create(Order order) {
        if (order.getAmount() == null || order.getAmount().doubleValue() <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(LocalDateTime.now());

        return repository.save(order);
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order update(Long id, Order updated) {
        Order existing = getById(id);

        existing.setAmount(updated.getAmount());

        return repository.save(existing);
    }

    public void delete(Long id) {
        Order order = getById(id);
        repository.delete(order);
    }

    public Order pay(Long id) {
        Order order = getById(id);

        if (order.getStatus() != OrderStatus.NEW) {
            throw new RuntimeException("Only NEW orders can be paid");
        }

        order.setStatus(OrderStatus.PAID);
        return repository.save(order);
    }


    public Order cancel(Long id) {
        Order order = getById(id);

        if (order.getStatus() == OrderStatus.PAID) {
            throw new RuntimeException("Paid order cannot be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        return repository.save(order);
    }
}