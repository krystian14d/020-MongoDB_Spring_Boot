package com.amigoscode.demo.fee;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "fees")
public class Fee {

    @Id
    private String feeId;
    private BigDecimal transactionValue;
    private double percentage;
}
