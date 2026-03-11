package com.retail.order_management.service.impl;

import com.retail.order_management.dto.DeliveryCharge;
import com.retail.order_management.dto.DeliveryChargesDTO;
import com.retail.order_management.dto.Order;
import com.retail.order_management.dto.OrderDTO;
import com.retail.order_management.exception.InvalidOrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl();
    }

    @Test
    void testCalculateDeliveryFeeUnder50() {
        OrderDTO orderDTO = new OrderDTO(Arrays.asList(
                new Order(1, "John", 30, false)
        ));

        DeliveryChargesDTO result = orderService.calculateDeliveryChargesForOrders(orderDTO);

        assertNotNull(result);
        assertEquals(1, result.getDeliveryCharges().size());
        assertEquals(5, result.getDeliveryCharges().get(0).getDeliveryFee());
    }

    @Test
    void testCalculateDeliveryFeeBetween50And100() {
        OrderDTO orderDTO = new OrderDTO(Arrays.asList(
                new Order(2, "Jane", 75, false)
        ));

        DeliveryChargesDTO result = orderService.calculateDeliveryChargesForOrders(orderDTO);

        assertNotNull(result);
        assertEquals(1, result.getDeliveryCharges().size());
        assertEquals(2, result.getDeliveryCharges().get(0).getDeliveryFee());
    }

    @Test
    void testCalculateDeliveryFeeOver100() {
        OrderDTO orderDTO = new OrderDTO(Arrays.asList(
                new Order(3, "Bob", 150, false)
        ));

        DeliveryChargesDTO result = orderService.calculateDeliveryChargesForOrders(orderDTO);

        assertNotNull(result);
        assertEquals(1, result.getDeliveryCharges().size());
        assertEquals(0, result.getDeliveryCharges().get(0).getDeliveryFee());
    }

    @Test
    void testSameDayDeliveryFeeUnder50() {
        OrderDTO orderDTO = new OrderDTO(Arrays.asList(
                new Order(4, "Alice", 40, true)
        ));

        DeliveryChargesDTO result = orderService.calculateDeliveryChargesForOrders(orderDTO);

        assertNotNull(result);
        assertEquals(1, result.getDeliveryCharges().size());
        assertEquals(15, result.getDeliveryCharges().get(0).getDeliveryFee());
    }

    @Test
    void testSameDayDeliveryFeeBetween50And100() {
        OrderDTO orderDTO = new OrderDTO(Arrays.asList(
                new Order(5, "Charlie", 75, true)
        ));

        DeliveryChargesDTO result = orderService.calculateDeliveryChargesForOrders(orderDTO);

        assertNotNull(result);
        assertEquals(1, result.getDeliveryCharges().size());
        assertEquals(12, result.getDeliveryCharges().get(0).getDeliveryFee());
    }

    @Test
    void testSameDayDeliveryFeeOver100() {
        OrderDTO orderDTO = new OrderDTO(Arrays.asList(
                new Order(6, "Diana", 120, true)
        ));

        DeliveryChargesDTO result = orderService.calculateDeliveryChargesForOrders(orderDTO);

        assertNotNull(result);
        assertEquals(1, result.getDeliveryCharges().size());
        assertEquals(10, result.getDeliveryCharges().get(0).getDeliveryFee());
    }

    @Test
    void testDeliveryChargesWithOderValueExactly50() {
        OrderDTO orderDTO = new OrderDTO(Arrays.asList(
                new Order(7, "Eve", 50, false)
        ));

        DeliveryChargesDTO result = orderService.calculateDeliveryChargesForOrders(orderDTO);

        assertNotNull(result);
        assertEquals(1, result.getDeliveryCharges().size());
        assertEquals(2, result.getDeliveryCharges().get(0).getDeliveryFee());
    }

    @Test
    void testDeliveryChargesWithOderValueExactly100() {
        OrderDTO orderDTO = new OrderDTO(Arrays.asList(
                new Order(8, "Frank", 100, false)
        ));

        DeliveryChargesDTO result = orderService.calculateDeliveryChargesForOrders(orderDTO);

        assertNotNull(result);
        assertEquals(1, result.getDeliveryCharges().size());
        assertEquals(0, result.getDeliveryCharges().get(0).getDeliveryFee());
    }

    @Test
    void testDeliveryChargesSortedByFeeDescending() {
        OrderDTO orderDTO = new OrderDTO(Arrays.asList(
                new Order(2, "Jane", 75, false),
                new Order(1, "John", 30, false),
                new Order(3, "Bob", 150, false)
        ));

        DeliveryChargesDTO result = orderService.calculateDeliveryChargesForOrders(orderDTO);

        assertNotNull(result);
        assertEquals(3, result.getDeliveryCharges().size());
        assertEquals(5, result.getDeliveryCharges().get(0).getDeliveryFee());
        assertEquals(2, result.getDeliveryCharges().get(1).getDeliveryFee());
        assertEquals(0, result.getDeliveryCharges().get(2).getDeliveryFee());
    }

}

