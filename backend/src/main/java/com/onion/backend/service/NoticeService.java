package com.onion.backend.service;

import com.onion.backend.dto.WriteNotice;
import com.onion.backend.entity.Notice;
import com.onion.backend.entity.User;
import com.onion.backend.entity.UserNotificationHistory;
import com.onion.backend.exception.ResourceNotFoundException;
import com.onion.backend.repository.NoticeRepository;
import com.onion.backend.repository.UserNotificationHistoryRepository;
import com.onion.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    private final UserRepository userRepository;

    private final UserNotificationHistoryRepository userNotificationHistoryRepository;

    public NoticeService(NoticeRepository noticeRepository, UserRepository userRepository, UserNotificationHistoryRepository userNotificationHistoryRepository) {
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
        this.userNotificationHistoryRepository = userNotificationHistoryRepository;
    }

    public Notice writeNotice(WriteNotice dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> author = userRepository.findByUsername(userDetails.getUsername());
        if (author.isEmpty()) {
            throw new ResourceNotFoundException("author not found");
        }
        Notice notice = new Notice();
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        notice.setAuthor(author.get());
        noticeRepository.save(notice);
        return notice;
    }

    public Notice getNotice(Long noticeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("user not found");
        }
        Optional<Notice> notice = noticeRepository.findById(noticeId);
        UserNotificationHistory userNotificationHistory = new UserNotificationHistory();
        userNotificationHistory.setTitle("공지사항이 작성되었습니다.");
        userNotificationHistory.setContent(notice.get().getTitle());
        userNotificationHistory.setUserId(user.get().getId());
        userNotificationHistory.setIsRead(true);
        userNotificationHistory.setNoticeId(noticeId);
        userNotificationHistory.setCreatedDate(notice.get().getCreatedDate());
        userNotificationHistory.setUpdatedDate(LocalDateTime.now());
        userNotificationHistoryRepository.save(userNotificationHistory);
        return notice.get();
    }
}
