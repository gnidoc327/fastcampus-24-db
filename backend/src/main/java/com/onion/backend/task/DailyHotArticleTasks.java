package com.onion.backend.task;

import com.onion.backend.entity.Article;
import com.onion.backend.entity.HotArticle;
import com.onion.backend.repository.ArticleRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DailyHotArticleTasks {
    public static final String YESTERDAY_REDIS_KEY = "yesterday-hot-article:";
    public static final String WEEK_REDIS_KEY = "week-hot-article:";

    private final ArticleRepository articleRepository;

    private RedisTemplate<String, Object> redisTemplate;

    public DailyHotArticleTasks(ArticleRepository articleRepository, RedisTemplate<String, Object> redisTemplate) {
        this.articleRepository = articleRepository;
        this.redisTemplate = redisTemplate;
    }


    @Scheduled(cron = "50 26 07 * * ?")
    public void pickYesterdayHotArticle() {
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Article article = articleRepository.findHotArticle(startDate, endDate);
        if (article == null) {
            return;
        }
        HotArticle hotArticle = new HotArticle();
        hotArticle.setId(article.getId());
        hotArticle.setTitle(article.getTitle());
        hotArticle.setContent(article.getContent());
        hotArticle.setAuthorName(article.getAuthor().getUsername());
        hotArticle.setCreatedDate(article.getCreatedDate());
        hotArticle.setUpdatedDate(article.getUpdatedDate());
        hotArticle.setViewCount(article.getViewCount());
        redisTemplate.opsForHash().put(YESTERDAY_REDIS_KEY + article.getId(), article.getId(), hotArticle);
    }

    @Scheduled(cron = "20 28 07 * * ?")
    public void pickWeekHotArticle() {
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(8), LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN);
        Article article = articleRepository.findHotArticle(startDate, endDate);
        if (article == null) {
            return;
        }
        HotArticle hotArticle = new HotArticle();
        hotArticle.setId(article.getId());
        hotArticle.setTitle(article.getTitle());
        hotArticle.setContent(article.getContent());
        hotArticle.setAuthorName(article.getAuthor().getUsername());
        hotArticle.setCreatedDate(article.getCreatedDate());
        hotArticle.setUpdatedDate(article.getUpdatedDate());
        hotArticle.setViewCount(article.getViewCount());
        redisTemplate.opsForHash().put(WEEK_REDIS_KEY + article.getId(), article.getId(), hotArticle);
    }
}
