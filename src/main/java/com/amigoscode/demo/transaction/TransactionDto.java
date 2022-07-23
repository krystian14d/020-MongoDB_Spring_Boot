package com.amigoscode.demo.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionDto {

    private String customerId;
    private String customerFirstName;
    private String customerLastName;
    private int numberOfTransactions;
    private Double totalTransactionsValue;
    private Double fee;
    private LocalDateTime latestTransactionDate;
}
