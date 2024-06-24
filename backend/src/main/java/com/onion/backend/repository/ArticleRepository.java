package com.onion.backend.repository;

import com.onion.backend.entity.Article;
import com.onion.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a WHERE a.board.id = :boardId ORDER BY a.createdDate DESC LIMIT 10")
    List<Article> findTop10ByBoardIdOrderByCreatedDateDesc(@Param("boardId") Long boardId);

    @Query("SELECT a FROM Article a WHERE a.board.id = :boardId AND a.id < :articleId ORDER BY a.createdDate DESC LIMIT 10")
    List<Article> findTop10ByBoardIdAndArticleIdLessThanOrderByCreatedDateDesc(@Param("boardId") Long boardId,
                                                                               @Param("articleId") Long articleId);

    @Query("SELECT a FROM Article a WHERE a.board.id = :boardId AND a.id > :articleId ORDER BY a.createdDate DESC LIMIT 10")
    List<Article> findTop10ByBoardIdAndArticleIdGreaterThanOrderByCreatedDateDesc(@Param("boardId") Long boardId,
                                                                                  @Param("articleId") Long articleId);

    @Query("SELECT a FROM Article a JOIN a.author u WHERE u.username = :username ORDER BY a.createdDate DESC LIMIT 1")
    Article findLatestArticleByAuthorUsernameOrderByCreatedDate(@Param("username") String username);

    @Query("SELECT a FROM Article a JOIN a.author u WHERE u.username = :username ORDER BY a.updatedDate DESC LIMIT 1")
    Article findLatestArticleByAuthorUsernameOrderByUpdatedDate(@Param("username") String username);
}
