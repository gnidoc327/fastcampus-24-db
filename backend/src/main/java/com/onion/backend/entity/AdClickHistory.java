package com.onion.backend.entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "adClickHistory")
@Getter
@Setter
public class AdClickHistory {
    @Id
    private String id;

    private Long adId;

    private String username;

    private String clientIp;

    private LocalDateTime createdDate = LocalDateTime.now();
}