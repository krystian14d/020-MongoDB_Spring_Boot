package com.amigoscode.demo.transaction;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/api")
    public List<TransactionDto> findTransactions(@RequestParam(name = "id") List<String> userIds){



        return null;
    }
}
