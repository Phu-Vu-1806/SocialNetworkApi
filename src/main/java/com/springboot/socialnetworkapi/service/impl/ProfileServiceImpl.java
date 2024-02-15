package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.Follow;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.User;
import com.springboot.socialnetworkapi.exception.CheckFollow;
import com.springboot.socialnetworkapi.exception.ExistsLocation;
import com.springboot.socialnetworkapi.payload.request.LocationRequest;
import com.springboot.socialnetworkapi.repository.FollowRepository;
import com.springboot.socialnetworkapi.repository.ProfileRepository;
import com.springboot.socialnetworkapi.repository.UserRepository;
import com.springboot.socialnetworkapi.security.service.UserDetailsImpl;
import com.springboot.socialnetworkapi.service.inf.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    public Profile getProfileByUsername(String username) {

        User user = userRepository.findByUsername(username).get();
        Profile profile = profileRepository.findByUser(user).get();

        return profile;
    }

    @Override
    public Profile getProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found by id"));
        Profile profile = profileRepository.findByUser(user).get();
        return profile;
    }

    @Override
    public Profile createProfile() {

        String location = randomLocation();
        while (profileRepository.existsByLocation(location)) {
            location = randomLocation();
        }

        String website = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/profile/")
                .path(location)
                .toUriString();
        Profile profile = Profile.builder()
                .joinDate(new Date())
                .location(location)
                .website(website)
                .build();
        profileRepository.save(profile);
        return profile;
    }

    public String randomLocation() {
        double randomDouble = Math.random();
        randomDouble = randomDouble * 100000000 + 10000000;
        int randomInt = (int) randomDouble;
        String location = String.valueOf(randomInt);
        return location;
    }

    @Override
    public Profile changeLocation(User user, LocationRequest location) {
        Profile profile = profileRepository.findByUser(user).get();
        if (profileRepository.existsByLocation(location.getLocation())) {
            throw new ExistsLocation(location + "already exists");
        }

        String website = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/profile/")
                .path(location.getLocation())
                .toUriString();

        profile.setLocation(location.getLocation());
        profile.setWebsite(website);
        profileRepository.save(profile);
        return profile;
    }

    @Override
    public void follow(Long fromUserId, Long toUserId) {
        Profile fromProfile = getProfileByUserId(fromUserId);
        Profile toProfile = getProfileByUserId(toUserId);

//        if (fromProfile.getFollowings().contains(toProfile)) {
//            throw new CheckFollow("You has been already followed this user");
//        }

        if (followRepository.existsByFollowerAndFollowing(toProfile, fromProfile)) {
            throw new CheckFollow("You has been already followed this user");
        }

        Follow follow = new Follow();
        follow.setFollowing(fromProfile);
        follow.setFollower(toProfile);

//        fromProfile.getFollowings().add(follow);

        followRepository.save(follow);

//        fromProfile.getFollowings().add(toProfile);
//        toProfile.getFollowers().add(fromProfile);
//
//        fromProfile.getFollowers().add(fromProfile);
//        toProfile.getFollowings().add(toProfile);

//        List<Profile> profiles = new ArrayList<>();
//        profiles.add(fromProfile);
//        profiles.add(toProfile);
//        profileRepository.saveAll(profiles);

//        Profile profile = new Profile();
//        profile.setFollowings(fromProfile.getFollowings());
//        profile.setFollowers(toProfile.getFollowers());
//        profileRepository.save(profile);

    }

    @Override
    public void unFollow(Long fromUserId, Long toUserId) {
        Profile fromProfile = getProfileByUserId(fromUserId);
        Profile toProfile = getProfileByUserId(toUserId);

        if (!fromProfile.getFollowings().contains(toProfile)) {
            throw new CheckFollow("You has not being followed this user");
        }

        Iterator<Follow> fromIterator = fromProfile.getFollowings().iterator();
        while (fromIterator.hasNext()) {
            Follow item = fromIterator.next();
            if (item.getFollowing().getId() == toUserId) {
                fromIterator.remove();
                followRepository.delete(item);
            }
        }

//        Iterator<Follow> toIterator = fromProfile.getFollowers().iterator();
//        while (toIterator.hasNext()){
//            Follow item = toIterator.next();
//            if(item.getUser().getId() == fromUserId){
//                toIterator.remove();
//            }
//        }

//        List<Profile> profiles = new ArrayList<>();
//        profiles.add(fromProfile);
//        profiles.add(toProfile);

//        profileRepository.saveAll(profiles);

    }

    @Override
    public boolean checkUser(Authentication authentication, Profile profile) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (profile.getUser().getUsername().equals(userDetails.getUsername())) {
            return true;
        } else {
            return false;
        }
    }
}
