package com.onion.backend.service;

import com.onion.backend.dto.AdHistoryResult;
import com.onion.backend.dto.AdvertisementDto;
import com.onion.backend.entity.*;
import com.onion.backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserNotificationHistoryService {
    UserNotificationHistoryRepository userNotificationHistoryRepository;

    public UserNotificationHistoryService(UserNotificationHistoryRepository userNotificationHistoryRepository) {
        this.userNotificationHistoryRepository = userNotificationHistoryRepository;
    }

    public void insertArticleNotification(Article article, Long userId) {
        UserNotificationHistory history = new UserNotificationHistory();
        history.setTitle("글이 작성되었습니다.");
        history.setContent(article.getTitle());
        history.setUserId(userId);
        userNotificationHistoryRepository.save(history);
    }

    public void insertCommentNotification(Comment comment, Long userId) {
        UserNotificationHistory history = new UserNotificationHistory();
        history.setTitle("댓글이 작성되었습니다.");
        history.setContent(comment.getContent());
        history.setUserId(userId);
        userNotificationHistoryRepository.save(history);
    }

    public void readNotification(String id) {
        Optional<UserNotificationHistory> history = userNotificationHistoryRepository.findById(id);
        if (history.isEmpty()) {
            return;
        }
        history.get().setIsRead(true);
        history.get().setUpdatedDate(LocalDateTime.now());
        userNotificationHistoryRepository.save(history.get());
    }
}
