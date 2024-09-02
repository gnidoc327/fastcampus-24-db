package com.onion.backend.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SendCommentNotification {
    private String type = "send_comment_notification";
    private Long commentId;
    private Long userId;
}
