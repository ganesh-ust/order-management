package com.retail.order_management.service.impl;

import com.retail.order_management.dto.DeliveryCharge;
import com.retail.order_management.dto.DeliveryChargesDTO;
import com.retail.order_management.dto.Order;
import com.retail.order_management.dto.OrderDTO;
import com.retail.order_management.exception.InvalidOrderException;
import com.retail.order_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Override
    public DeliveryChargesDTO calculateDeliveryChargesForOrders(OrderDTO orderDTO) {
        log.info("Calculating delivery charges for orders");
        List<Order> orders = orderDTO.getOrders();
        List<DeliveryCharge> deliveryCharges = orders.stream()
                .map(this::calculateDeliveryFee)
                .sorted((d1, d2) -> Long.compare(d2.getDeliveryFee(), d1.getDeliveryFee()))
                .collect(Collectors.toCollection(LinkedList::new));

        DeliveryChargesDTO deliveryChargesDTO = new DeliveryChargesDTO(deliveryCharges);
        log.info("Calculated delivery charges for orders");
        return deliveryChargesDTO;
    }

    private DeliveryCharge calculateDeliveryFee(Order order) {
        log.info("Calculating delivery fee for order ID: {}", order.getId());
        long deliveryFee;
        if(order.getOrderValue() <= 0) {
            log.warn("Order value is invalid: {} for orderId: {}", order.getOrderValue(), order.getId());
            throw new InvalidOrderException("Order value must be greater than 0 for order ID: " + order.getId());
        } else if (order.getOrderValue() < 50) {
            deliveryFee = 5;
        } else if (order.getOrderValue() < 100) {
            deliveryFee = 2;
        } else {
            deliveryFee = 0;
        }

        if(order.isSameDay()){
            deliveryFee += 10;
        }
        log.info("Calculated delivery fee for order ID: {} is {}", order.getId(), deliveryFee);
        return new DeliveryCharge(order.getId(), order.getCustomer(), deliveryFee);
    }
}

