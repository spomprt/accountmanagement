package ru.app.accountmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private Long id;
    private Long userId;
    private String planName;
    private BigDecimal price;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
}