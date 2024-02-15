package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.User;
import com.springboot.socialnetworkapi.payload.request.LocationRequest;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface ProfileService {
    Profile getProfileByUserId(Long userId);
    Profile createProfile();
    Profile changeLocation(User user, LocationRequest location);
    void follow(Long fromUserId, Long toUserId);
    void unFollow(Long fromUserId, Long toUserId);
    boolean checkUser(Authentication authentication, Profile profile);
}
