package com.onion.backend.controller;

import com.onion.backend.dto.WriteDeviceDto;
import com.onion.backend.dto.WriteNotice;
import com.onion.backend.entity.Device;
import com.onion.backend.entity.Notice;
import com.onion.backend.service.NoticeService;
import com.onion.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("")
    public ResponseEntity<Notice> addNotice(@RequestBody WriteNotice dto) {
        return ResponseEntity.ok(noticeService.writeNotice(dto));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<Notice> getNotice(@PathVariable Long noticeId) {
        return ResponseEntity.ok(noticeService.getNotice(noticeId));
    }
}
