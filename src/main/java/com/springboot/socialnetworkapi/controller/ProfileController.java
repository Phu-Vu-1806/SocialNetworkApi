package com.springboot.socialnetworkapi.controller;

import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.User;
import com.springboot.socialnetworkapi.payload.request.LocationRequest;
import com.springboot.socialnetworkapi.payload.response.CommentResponse;
import com.springboot.socialnetworkapi.payload.response.ObjectResponse;
import com.springboot.socialnetworkapi.repository.ProfileRepository;
import com.springboot.socialnetworkapi.repository.UserRepository;
import com.springboot.socialnetworkapi.security.service.UserDetailsImpl;
import com.springboot.socialnetworkapi.service.impl.CommentServiceImpl;
import com.springboot.socialnetworkapi.service.impl.ProfileServiceImpl;
import com.springboot.socialnetworkapi.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileServiceImpl profileService;

    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping("/{location}")
    public ResponseEntity<?> getProfile(@PathVariable(name = "location") String location){
        Profile profile = profileRepository.findByLocation(location).get();
        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    @GetMapping("/getProfileById/{profileId}")
    public ResponseEntity<?> getProfileByProfileId(@PathVariable(name = "profileId") Long profileId){
        Profile profile = profileRepository.findById(profileId).get();
        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    @GetMapping("/getProfileById/{userId}")
    public ResponseEntity<?> getProfileByUserId(@PathVariable(name = "userId") Long userId){
        User user = userRepository.findById(userId).get();
        Profile profile = profileRepository.findByUser(user).get();
        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    @GetMapping("/myProfile")
    public ResponseEntity<?> getProfileByUser(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        Profile profile = profileRepository.findByUser(user).get();

        return ResponseEntity.status(HttpStatus.OK).body(profile);
    }

    @PutMapping("/change-location")
    public ResponseEntity<?> changeLocation(@RequestBody LocationRequest location, Authentication authentication){

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        profileService.changeLocation(user,location);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Change location successfully"));
    }

    @GetMapping("/follow")
    public ResponseEntity<?> follow(@RequestParam(name = "fromUserId") Long fromUserId, @RequestParam(name = "toUserId") Long toUserId){

        profileService.follow(fromUserId, toUserId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("You have been follow this user"));
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<?> unfollow(@RequestParam(name = "fromUserId") Long fromUserId, @RequestParam(name = "toUserId") Long toUserId){

        profileService.unFollow(fromUserId, toUserId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("You have been unfollow this user"));
    }

    @GetMapping("/comment/{location}")
    public ResponseEntity<?> getAllCommentFromProfile(@PathVariable(name = "location") String location){

        Profile profile = profileRepository.findByLocation(location).get();
        List<CommentResponse> commentResponses = commentService.getCommentFromProfile(profile);

        return ResponseEntity.ok().body(new ObjectResponse(profile.getUser().getName(), profile.getLocation(), commentResponses));
    }
}
