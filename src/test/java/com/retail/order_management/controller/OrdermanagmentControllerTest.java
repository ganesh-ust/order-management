package com.retail.order_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.order_management.dto.DeliveryCharge;
import com.retail.order_management.dto.DeliveryChargesDTO;
import com.retail.order_management.dto.Order;
import com.retail.order_management.dto.OrderDTO;
import com.retail.order_management.exception.InvalidOrderException;
import com.retail.order_management.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdermanagmentController.class)
class OrdermanagmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    private ObjectMapper objectMapper;
    private OrderDTO orderDTO;
    private DeliveryChargesDTO deliveryChargesDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        orderDTO = new OrderDTO(Arrays.asList(
                new Order(1, "Alice", 40, false),
                new Order(2, "Bob", 75, true),
                new Order(3, "Charlie", 150, false)
        ));

        deliveryChargesDTO = new DeliveryChargesDTO(Arrays.asList(
                new DeliveryCharge(2, "Bob", 12),
                new DeliveryCharge(1, "Alice", 5),
                new DeliveryCharge(3, "Charlie", 0)
        ));
    }

    @Test
    void testCalculateDeliveryChargesSuccess() throws Exception {

        when(orderService.calculateDeliveryChargesForOrders(any(OrderDTO.class)))
                .thenReturn(deliveryChargesDTO);

        mockMvc.perform(post("/orders/delivery-fee")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void testCalculateDeliveryChargesWithException() throws Exception {
        when(orderService.calculateDeliveryChargesForOrders(any(OrderDTO.class)))
                .thenThrow(new InvalidOrderException("Invalid order data"));

        mockMvc.perform(post("/orders/delivery-fee")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isBadRequest());
    }
}
