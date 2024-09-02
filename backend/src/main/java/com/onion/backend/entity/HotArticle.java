package com.onion.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HotArticle implements Serializable {
    private Long id;

    private String title;

    private String content;

    private String authorName;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Long viewCount = 0L;
}