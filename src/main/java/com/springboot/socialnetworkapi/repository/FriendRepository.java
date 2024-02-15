package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.Friend;
import com.springboot.socialnetworkapi.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

//    @Query(value = "SELECT * FROM friends WHERE friends.name LIKE %?1%", nativeQuery = true)
//    Page<Friend> findAllFriendByName(String name, Pageable pageable);

    boolean existsByFriendConfirmAndFriendAdd(Profile friendConfirm, Profile friendAdd);

    @Modifying
    @Query(value = "DELETE FROM Friend f WHERE f.friendConfirm = ?1 AND f.friendAdd = ?2")
    void unfriend(Profile friendConfirm, Profile friendAdd);

    @Query(value = "SELECT * FROM Friend f WHERE f.friendConfirm = ?1 OR f.friendAdd = ?2", nativeQuery = true)
    List<Friend> getFriend(Profile friendConfirm, Profile friendAdd );
}
