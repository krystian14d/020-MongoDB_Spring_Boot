package com.amigoscode.demo.transaction;

import com.amigoscode.demo.fee.FeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final FeeRepository feeRepository;

    public List<TransactionDto> findTransactionsById(List<String> customerIds) {

        List<TransactionDto> transactionsDto = new ArrayList<>();
        List<Transaction> transactions;

        if (customerIds.contains("all")) {
            transactions = transactionRepository.findAll();
        } else {
            transactions = transactionRepository.findAllByCustomerIdIsIn(customerIds);
        }

        Map<String, List<Transaction>> transactionsByCustomerId = transactions
                .stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId));

        transactionsByCustomerId
                .forEach(
                        (customerId, customerTransactions) -> {

                            String customerFirstName = getCustomerFirstName(customerTransactions);
                            String customerLastName = getCustomerLastName(customerTransactions);
                            double totalTransactionsValue = getTotalTransactionsValue(customerTransactions);
                            int numberOfTransactions = customerTransactions.size();
                            LocalDateTime latestTransactionDate = getLatestTransactionDate(customerTransactions);
                            double fee = getFeeByTransactionsValue(totalTransactionsValue);

                            transactionsDto.add(new TransactionDto(
                                    customerId,
                                    customerFirstName,
                                    customerLastName,
                                    numberOfTransactions,
                                    totalTransactionsValue,
                                    fee,
                                    latestTransactionDate
                            ));
                        }
                );
        return transactionsDto;
    }

    private LocalDateTime getLatestTransactionDate(List<Transaction> customerTransactions) {
        return customerTransactions
                .stream()
                .map(Transaction::getTransactionDate)
                .max(Comparator.naturalOrder())
                .get();
    }

    private double getTotalTransactionsValue(List<Transaction> customerTransactions) {
        return customerTransactions
                .stream()
                .map(Transaction::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();
    }

    private String getCustomerLastName(List<Transaction> customerTransactions) {
        return customerTransactions
                .stream()
                .map(Transaction::getCustomerLastName)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private String getCustomerFirstName(List<Transaction> customerTransactions) {
        return customerTransactions
                .stream()
                .map(Transaction::getCustomerFirstName)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private double getFeeByTransactionsValue(Double transactionValue) {

        if (transactionValue >= 10000.00) {
            return 0;
        } else if (transactionValue == 0.00 || transactionValue.isNaN()) {
            return 10.00;
        } else {
            return feeRepository.findAll()
                    .stream()
                    .filter(f -> f.getTransactionValue() > transactionValue)
                    .map(f -> f.getPercentage())
                    .max(Comparator.naturalOrder())
                    .orElse(10.00);
        }
    }
}
