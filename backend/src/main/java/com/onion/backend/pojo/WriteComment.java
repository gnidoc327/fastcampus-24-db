package com.onion.backend.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class WriteComment {
    private String type = "write_comment";
    private Long commentId;
}
