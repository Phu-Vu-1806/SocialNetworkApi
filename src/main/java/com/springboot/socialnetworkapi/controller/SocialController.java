package com.springboot.socialnetworkapi.controller;

import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.Social;
import com.springboot.socialnetworkapi.payload.request.SocialRequest;
import com.springboot.socialnetworkapi.repository.ProfileRepository;
import com.springboot.socialnetworkapi.security.service.UserDetailsImpl;
import com.springboot.socialnetworkapi.service.impl.ProfileServiceImpl;
import com.springboot.socialnetworkapi.service.impl.SocialServiceImpl;
import com.springboot.socialnetworkapi.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/social")
public class SocialController {

    @Autowired
    private SocialServiceImpl socialService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileServiceImpl profileService;

    @GetMapping("/{location}/get")
    public ResponseEntity<?> getByProfile(@PathVariable(name = "location") String location) {
        Profile profile = profileRepository.findByLocation(location).get();
        Social social = profile.getSocial();
        return ResponseEntity.ok().body(social);
    }

    @PostMapping("/{location}/add")
    public ResponseEntity<ResponseMessage> addSocial(@RequestBody SocialRequest socialRequest,
                                                     @PathVariable(name = "location") String location,
                                                     Authentication authentication) {
        Profile profile = profileRepository.findByLocation(location).get();

//        chỉ cho người dùng của profile mới có thể chỉnh sửa thông tin
        if (profileService.checkUser(authentication, profile)) {
            Social social = socialService.addSocial(socialRequest, profile);
            return ResponseEntity.ok().body(new ResponseMessage("Success", social));
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PutMapping("/{location}/update")
    public ResponseEntity<ResponseMessage> updateSocial(@RequestBody SocialRequest socialRequest,
                                                        @PathVariable(name = "location") String location,
                                                        Authentication authentication) {
        Profile profile = profileRepository.findByLocation(location).get();

        if (profileService.checkUser(authentication, profile)) {
            Social social = socialService.updateSocial(socialRequest, profile);
            return ResponseEntity.ok().body(new ResponseMessage("Success", social));
        }else {
            return ResponseEntity.status(401).build();
        }

    }

    @DeleteMapping("/{location}/delete")
    public ResponseEntity<ResponseMessage> deleteSocial(@PathVariable(name = "location") String location,
                                                        Authentication authentication) {
        Profile profile = profileRepository.findByLocation(location).get();
        if (profileService.checkUser(authentication, profile)) {
            socialService.deleteSocial(profile);
            return ResponseEntity.ok().body(new ResponseMessage("Success"));
        }else {
            return ResponseEntity.status(401).build();
        }
    }


}
