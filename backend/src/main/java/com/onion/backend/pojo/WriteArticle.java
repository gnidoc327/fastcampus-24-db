package com.onion.backend.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class WriteArticle {
    private String type = "write_article";
    private Long articleId;
    private Long userId;
}
