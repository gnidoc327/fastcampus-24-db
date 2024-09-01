package com.onion.backend.repository;

import com.onion.backend.entity.AdClickHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdClickHistoryRepository extends MongoRepository<AdClickHistory, String> {
}
