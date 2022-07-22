package com.amigoscode.demo.transaction;

import com.amigoscode.demo.fee.FeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final FeeRepository feeRepository;

    public List<TransactionDto> findTransactionsById(List<String> customerIds){
        List<Transaction> transactions = transactionRepository.findAllByCustomerId(customerIds);

    }
}
