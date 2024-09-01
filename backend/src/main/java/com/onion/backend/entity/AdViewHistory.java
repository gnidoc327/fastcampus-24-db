package com.onion.backend.entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "adViewHistory")
@Getter
@Setter
public class AdViewHistory {
    @Id
    private String id;

    private Long adId;

    private String username;

    private String clientIp;

    private Boolean isTrueView = false;

    private LocalDateTime createdDate = LocalDateTime.now();
}