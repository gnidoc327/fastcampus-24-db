package com.onion.backend.controller;

import com.onion.backend.dto.EditArticleDto;
import com.onion.backend.dto.WriteArticleDto;
import com.onion.backend.dto.WriteCommentDto;
import com.onion.backend.entity.Article;
import com.onion.backend.entity.Comment;
import com.onion.backend.service.ArticleService;
import com.onion.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{boardId}/articles/{articleId}/comments")
    public ResponseEntity<Comment> writeComment(@PathVariable Long boardId,
                                                @PathVariable Long articleId,
                                                @RequestBody WriteCommentDto writeCommentDto) {
        return ResponseEntity.ok(commentService.writeComment(boardId, articleId, writeCommentDto));
    }

    @PutMapping("/{boardId}/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<Comment> writeComment(@PathVariable Long boardId,
                                                @PathVariable Long articleId,
                                                @PathVariable Long commentId,
                                                @RequestBody WriteCommentDto editCommentDto) {
        return ResponseEntity.ok(commentService.editComment(boardId, articleId, commentId, editCommentDto));
    }

    @DeleteMapping("/{boardId}/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<String> writeComment(@PathVariable Long boardId,
                                                @PathVariable Long articleId,
                                                @PathVariable Long commentId) {
        commentService.deleteComment(boardId, articleId, commentId);
        return ResponseEntity.ok("comment is deleted");
    }
}
