package com.onion.backend.repository;

import com.onion.backend.entity.Article;
import com.onion.backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN c.author u WHERE u.username = :username ORDER BY c.createdDate DESC LIMIT 1")
    Comment findLatestCommentOrderByCreatedDate(@Param("username") String username);

    @Query("SELECT c FROM Comment c JOIN c.author u WHERE u.username = :username ORDER BY c.updatedDate DESC LIMIT 1")
    Comment findLatestCommentOrderByUpdatedDate(@Param("username") String username);

}
