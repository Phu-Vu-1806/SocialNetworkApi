package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.User;

import java.util.List;

public interface FriendService {
    void addFriend(Long fromUserId, Long toUserId);
    void cancelRequest(Long fromUserId, Long toUserId);
    void unfriend(Long fromUserId, Long toUserId);
    void acceptFriend(Long confirmId, Long toUserId);
    List<User> getAllFriendRequest(Long userId);
    List<User> getAllFriend(Long userId);
    List<User> findAllFriendByName(String name, Integer pageNo, Integer pageSize, String sortBy);
}
