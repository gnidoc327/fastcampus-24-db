package com.onion.backend.dto;

import lombok.Getter;

@Getter
public class WriteArticleDto {
    Long boardId;
    String title;
    String content;
}
