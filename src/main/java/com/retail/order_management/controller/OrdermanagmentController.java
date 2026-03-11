package com.retail.order_management.controller;

import com.retail.order_management.dto.DeliveryChargesDTO;
import com.retail.order_management.dto.OrderDTO;
import com.retail.order_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrdermanagmentController {

    private final OrderService orderService;

    @PostMapping("/delivery-fee")
    public ResponseEntity<DeliveryChargesDTO> calculateDeliveryCharges(@RequestBody OrderDTO orderDTO) {
        log.info("REST request to calculate delivery charges for online retail orders");
        DeliveryChargesDTO deliveryCharges = orderService.calculateDeliveryChargesForOrders(orderDTO);
        log.info("Delivery charges calculated successfully");
        return ResponseEntity.ok(deliveryCharges);
    }
}
