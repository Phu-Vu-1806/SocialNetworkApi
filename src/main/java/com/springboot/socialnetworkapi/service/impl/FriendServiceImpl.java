package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.Friend;
import com.springboot.socialnetworkapi.entity.FriendRequest;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.User;
import com.springboot.socialnetworkapi.repository.FriendRepository;
import com.springboot.socialnetworkapi.repository.FriendRequestRepository;
import com.springboot.socialnetworkapi.service.inf.FriendService;
import com.springboot.socialnetworkapi.service.inf.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private ProfileServiceImpl profileService;

    @Override
    public void addFriend(Long fromUserId, Long toUserId) {

        Profile fromProfile = profileService.getProfileByUserId(fromUserId);
        Profile toProfile = profileService.getProfileByUserId(toUserId);

//        List<FriendRequest> friendRequests = fromProfile.getFriendsRequests();
//        friendRequests.addAll(toProfile.getFriendsResponse());
//        for (FriendRequest friendRequest : friendRequests){
//            if ((friendRequest.getFriendRequest().equals(fromProfile) && friendRequest.getFriendResponse().equals(toProfile)) ||
//                    (friendRequest.getFriendRequest().equals(toProfile) && friendRequest.getFriendResponse().equals(fromProfile))){
//                throw new RuntimeException("Sent a friend request to this user");
//            }
//        }
//
//        List<Friend> friends = toProfile.getFriendsConfirm();
//        friends.addAll(fromProfile.getFriendsAdd());
//        for(Friend friend : friends){
//            if((friend.getFriendAdd().equals(fromProfile) && friend.getFriendConfirm().equals(toProfile)) ||
//                    (friend.getFriendAdd().equals(toProfile) && friend.getFriendConfirm().equals(fromProfile))){
//                throw new RuntimeException("This user is already a friend");
//            }
//        }

        if(friendRepository.existsByFriendConfirmAndFriendAdd(fromProfile, toProfile) ||
                friendRepository.existsByFriendConfirmAndFriendAdd(toProfile, fromProfile)){
            throw new RuntimeException("This user is already a friend");
        }

        if(friendRequestRepository.existsByFriendRequestAndFriendResponse(fromProfile, toProfile) ||
                friendRequestRepository.existsByFriendRequestAndFriendResponse(toProfile, fromProfile)){
            throw new RuntimeException("Sent a friend request to this user");
        }
        FriendRequest friendRequest = new FriendRequest(fromProfile, toProfile);
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public void cancelRequest(Long fromUserId, Long toUserId) {

        Profile fromProfile = profileService.getProfileByUserId(fromUserId);
        Profile toProfile = profileService.getProfileByUserId(toUserId);

//        cancel request from friend request
        if(friendRequestRepository.existsByFriendRequestAndFriendResponse(fromProfile, toProfile)){
            friendRequestRepository.deleteByFriendRequest(fromProfile, toProfile);
        }

//        cancel request from friend response
        if(friendRequestRepository.existsByFriendRequestAndFriendResponse(toProfile, fromProfile)){
            friendRequestRepository.deleteByFriendRequest(toProfile, fromProfile);
        }
    }

    @Override
    public void unfriend(Long fromUserId, Long toUserId){

        Profile fromProfile = profileService.getProfileByUserId(fromUserId);
        Profile toProfile = profileService.getProfileByUserId(toUserId);

//        unfriend from sender
        if(friendRepository.existsByFriendConfirmAndFriendAdd(fromProfile, toProfile)){
            friendRepository.unfriend(fromProfile, toProfile);
        }
//        unfriend from recipient
        if(friendRepository.existsByFriendConfirmAndFriendAdd(toProfile, fromProfile)){
            friendRepository.unfriend(toProfile, fromProfile);
        }
    }

    @Override
    public void acceptFriend(Long confirmId, Long toUserId) {

        Profile confirmProfile = profileService.getProfileByUserId(confirmId);
        Profile toProfile = profileService.getProfileByUserId(toUserId);

        if(!friendRequestRepository.existsByFriendRequestAndFriendResponse(toProfile, confirmProfile)){
            throw new RuntimeException("Couldn't find friend request");
        }

        if(friendRepository.existsByFriendConfirmAndFriendAdd(confirmProfile, toProfile) ||
                friendRepository.existsByFriendConfirmAndFriendAdd(toProfile, confirmProfile)){
            throw new RuntimeException("This user is already a friend");
        }

//        List<Friend> friends = confirmProfile.getFriendsConfirm();
//        friends.addAll(toProfile.getFriendsAdd());
//        for(Friend friend : friends){
//            if((friend.getFriendAdd().equals(toProfile) && friend.getFriendConfirm().equals(confirmProfile)) ||
//                    (friend.getFriendAdd().equals(confirmProfile) && friend.getFriendConfirm().equals(toProfile))){
//                throw new RuntimeException("This user is already a friend");
//            }
//        }

        List<FriendRequest> friendRequests = toProfile.getFriendsRequests();
        Iterator<FriendRequest> friendRequest = friendRequests.iterator();
        while (friendRequest.hasNext()){
            FriendRequest item = friendRequest.next();
            if(item.getFriendResponse().equals(confirmProfile) && item.getFriendRequest().equals(toProfile)){
                friendRequest.remove();
                friendRequestRepository.deleteByFriendRequest(toProfile, confirmProfile);
            }
        }

        Friend friend = new Friend(confirmProfile, toProfile);
        Profile profile = new Profile();
        profile.getFriendsConfirm().add(friend);
        friendRepository.save(friend);
    }

    @Override
    public List<User> getAllFriendRequest(Long userId) {
        return null;
    }

    @Override
    public List<User> getAllFriend(Long userId) {
        return null;
    }

    @Override
    public List<User> findAllFriendByName(String name, Integer pageNo, Integer pageSize, String sortBy) {
        return null;
    }

}
