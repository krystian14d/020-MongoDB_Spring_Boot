package com.amigoscode.demo.fee;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeeRepository extends MongoRepository<Fee, String> {
}
