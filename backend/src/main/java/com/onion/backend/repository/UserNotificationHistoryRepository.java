package com.onion.backend.repository;

import com.onion.backend.entity.UserNotificationHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserNotificationHistoryRepository extends MongoRepository<UserNotificationHistory, String> {
}
