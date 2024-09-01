package com.onion.backend.service;

import com.onion.backend.dto.AdvertisementDto;
import com.onion.backend.entity.AdClickHistory;
import com.onion.backend.entity.AdViewHistory;
import com.onion.backend.entity.Advertisement;
import com.onion.backend.repository.AdClickHistoryRepository;
import com.onion.backend.repository.AdViewHistoryRepository;
import com.onion.backend.repository.AdvertisementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementService {
    private static final String REDIS_KEY = "ad:";

    AdvertisementRepository advertisementRepository;

    AdViewHistoryRepository adViewHistoryRepository;

    AdClickHistoryRepository adClickHistoryRepository;

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository, RedisTemplate<String, Object> redisTemplate,
                                AdViewHistoryRepository adViewHistoryRepository, AdClickHistoryRepository adClickHistoryRepository) {
        this.advertisementRepository = advertisementRepository;
        this.redisTemplate = redisTemplate;
        this.adViewHistoryRepository = adViewHistoryRepository;
        this.adClickHistoryRepository = adClickHistoryRepository;
    }

    @Transactional
    public Advertisement writeAd(AdvertisementDto advertisementDto) {
        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(advertisementDto.getTitle());
        advertisement.setContent(advertisementDto.getContent());
        advertisement.setIsDeleted(advertisementDto.getIsDeleted());
        advertisement.setIsVisible(advertisementDto.getIsVisible());
        advertisement.setStartDate(advertisementDto.getStartDate());
        advertisement.setEndDate(advertisementDto.getEndDate());
        advertisement.setViewCount(advertisementDto.getViewCount());
        advertisement.setClickCount(advertisementDto.getClickCount());
        advertisementRepository.save(advertisement);
        redisTemplate.opsForHash().put(REDIS_KEY + advertisement.getId(), advertisement.getId(), advertisement);
        return advertisement;
    }

    public List<Advertisement> getAdList() {
        return advertisementRepository.findAll();
    }

    public Optional<Advertisement> getAd(Long adId, String clientIp, Boolean isTrueView) {
        this.insertAdViewHistory(adId, clientIp, isTrueView);
        Object tempObj = redisTemplate.opsForHash().get(REDIS_KEY, adId);
        if (tempObj != null) {
            return Optional.ofNullable((Advertisement) redisTemplate.opsForHash().get(REDIS_KEY, adId));
        }
        return advertisementRepository.findById(adId);
    }

    public void clickAd(Long adId, String clientIp) {
        AdClickHistory adClickHistory = new AdClickHistory();
        adClickHistory.setAdId(adId);
        adClickHistory.setClientIp(clientIp);
        adClickHistory.setCreatedDate(LocalDateTime.now());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!principal.equals("anonymousUser")) {
            UserDetails userDetails = (UserDetails) principal;
            adClickHistory.setUsername(userDetails.getUsername());
        }
        adClickHistoryRepository.save(adClickHistory);
    }

    private void insertAdViewHistory(Long adId, String clientIp, Boolean isTrueView) {
        AdViewHistory adViewHistory = new AdViewHistory();
        adViewHistory.setAdId(adId);
        adViewHistory.setClientIp(clientIp);
        adViewHistory.setIsTrueView(isTrueView);
        adViewHistory.setCreatedDate(LocalDateTime.now());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!principal.equals("anonymousUser")) {
            UserDetails userDetails = (UserDetails) principal;
            adViewHistory.setUsername(userDetails.getUsername());
        }
        adViewHistoryRepository.save(adViewHistory);
    }
}
