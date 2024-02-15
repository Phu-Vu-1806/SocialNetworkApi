package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.Notification;
import com.springboot.socialnetworkapi.repository.NotificationRepository;
import com.springboot.socialnetworkapi.service.inf.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAll(Long profileId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Notification> notifications = notificationRepository.getAllByToId(profileId, pageable);
        if (notifications.hasContent()) {
            return notifications.getContent();
        } else {
            return new ArrayList<Notification>();
        }
    }
}
