package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAll(Long profileId, Integer pageNo, Integer pageSize, String sortBy);
}
