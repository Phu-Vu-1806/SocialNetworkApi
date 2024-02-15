package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.FriendRequest;
import com.springboot.socialnetworkapi.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {

    boolean existsByFriendRequestAndFriendResponse(Profile friendRequest, Profile firendResponse);

    Optional<FriendRequest> findByFriendRequestAndFriendResponse(Profile friendRequest, Profile friendResponse);

    @Modifying
    @Query(value = "DELETE FROM FriendRequest f WHERE f.friendRequest = ?1 AND f.friendResponse = ?2")
    void deleteByFriendRequest(Profile friendRequest, Profile friendResponse);
}


