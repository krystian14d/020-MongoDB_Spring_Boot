package com.amigoscode.demo;

import com.amigoscode.demo.dbDataLoader.FeePercentageDataLoader;
import com.amigoscode.demo.dbDataLoader.TransactionDataLoader;
import com.amigoscode.demo.fee.Fee;
import com.amigoscode.demo.fee.FeeRepository;
import com.amigoscode.demo.transaction.Transaction;
import com.amigoscode.demo.transaction.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            TransactionRepository transactionRepository,
            FeeRepository feeRepository
    ){
        return args -> {
            TransactionDataLoader transactionDataLoader = new TransactionDataLoader();
            List<Transaction> transactions = transactionDataLoader.readCsv();
            transactionRepository.saveAll(transactions);

            List<Fee> fees = FeePercentageDataLoader.readCsv();
            feeRepository.saveAll(fees);
        };
    }

}
