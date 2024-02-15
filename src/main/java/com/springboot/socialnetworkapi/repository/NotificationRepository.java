package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

    @Query(value = "SELECT * FROM Notification n WHERE n.to_id = ?1", nativeQuery = true)
    Page<Notification> getAllByToId(Long profileId, Pageable pageable);
}
