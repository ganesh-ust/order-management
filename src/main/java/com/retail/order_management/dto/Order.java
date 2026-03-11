package com.retail.order_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private long id;
    private String customer;
    private long orderValue;
    private boolean sameDay;
}
