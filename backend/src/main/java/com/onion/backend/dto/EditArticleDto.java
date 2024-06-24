package com.onion.backend.dto;

import lombok.Getter;

import java.util.Optional;

@Getter
public class EditArticleDto {
    Optional<String> title;
    Optional<String> content;
}
