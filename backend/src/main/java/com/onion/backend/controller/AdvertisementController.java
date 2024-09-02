package com.onion.backend.controller;

import com.onion.backend.dto.AdHistoryResult;
import com.onion.backend.dto.AdvertisementDto;
import com.onion.backend.entity.Advertisement;
import com.onion.backend.service.AdvertisementService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping("/admin/ads")
    public ResponseEntity<Advertisement> writeAd(@RequestBody AdvertisementDto advertisementDto) {
        Advertisement advertisement = advertisementService.writeAd(advertisementDto);
        return ResponseEntity.ok(advertisement);
    }

    @GetMapping("/ads")
    public ResponseEntity<List<Advertisement>> getAdList() {
        List<Advertisement> advertisementList = advertisementService.getAdList();
        return ResponseEntity.ok(advertisementList);
    }

    @GetMapping("/ads/{adId}")
    public Object getAdList(@PathVariable Long adId, HttpServletRequest request, @RequestParam(required = false) Boolean isTrueView) {
        String ipAddress = request.getRemoteAddr();
        Optional<Advertisement> advertisement = advertisementService.getAd(adId, ipAddress, isTrueView != null && isTrueView);
        if (advertisement.isEmpty()) {
            return ResponseEntity.notFound();
        }
        return ResponseEntity.ok(advertisement);
    }

    @PostMapping("/ads/{adId}")
    public Object clickAd(@PathVariable Long adId, HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        advertisementService.clickAd(adId, ipAddress);
        return ResponseEntity.ok("click");
    }

    @GetMapping("/ads/history")
    public ResponseEntity<List<AdHistoryResult>> getAdHistory() {
        List<AdHistoryResult> result = advertisementService.getAdViewHistoryGroupedByAdId();
        advertisementService.insertAdViewStat(result);
        return ResponseEntity.ok(result);
    }
}
