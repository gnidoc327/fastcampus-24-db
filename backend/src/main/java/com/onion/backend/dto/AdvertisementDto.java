package com.onion.backend.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdvertisementDto {
    private String title;
    private String content;
    private Boolean isDeleted = false;
    private Boolean isVisible = true;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer viewCount = 0;
    private Integer clickCount = 0;
}