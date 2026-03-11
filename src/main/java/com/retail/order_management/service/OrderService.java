package com.retail.order_management.service;

import com.retail.order_management.dto.DeliveryChargesDTO;
import com.retail.order_management.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    DeliveryChargesDTO calculateDeliveryChargesForOrders(OrderDTO orderDTO);
}
