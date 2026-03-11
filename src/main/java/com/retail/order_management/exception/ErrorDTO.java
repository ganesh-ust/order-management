package com.retail.order_management.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDTO {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}
