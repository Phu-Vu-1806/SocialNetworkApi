package com.springboot.socialnetworkapi.controller;

import com.springboot.socialnetworkapi.entity.Education;
import com.springboot.socialnetworkapi.entity.Experience;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.Skill;
import com.springboot.socialnetworkapi.payload.request.ExperienceRequest;
import com.springboot.socialnetworkapi.repository.ProfileRepository;
import com.springboot.socialnetworkapi.security.service.UserDetailsImpl;
import com.springboot.socialnetworkapi.service.impl.ExperienceServiceImpl;
import com.springboot.socialnetworkapi.service.impl.ProfileServiceImpl;
import com.springboot.socialnetworkapi.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experience")
public class ExperienceController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ExperienceServiceImpl experienceService;

    @Autowired
    private ProfileServiceImpl profileService;

    @GetMapping("/{location}/get")
    public ResponseEntity<?> getExperience(@PathVariable(name = "location") String location) {
        Profile profile = profileRepository.findByLocation(location).get();
        List<Experience> experiences = profile.getExperiences();

        return ResponseEntity.ok().body(new ResponseMessage("Success", experiences));
    }

    @PostMapping("/{location}/add")
    public ResponseEntity<?> addExperience(@RequestBody ExperienceRequest experienceRequest,
                                           @PathVariable(name = "location") String location,
                                           Authentication authentication) {
        Profile profile = profileRepository.findByLocation(location).get();
        if(profileService.checkUser(authentication, profile)){
            List<Experience> experiences = experienceService.addExperience(experienceRequest, profile);
            return ResponseEntity.ok().body(new ResponseMessage("Success", experiences));
        }else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/{location}/update/{experienceId}")
    public ResponseEntity<?> updateExperience(@RequestBody ExperienceRequest experienceRequest,
                                              @PathVariable(name = "experienceId") Long experienceId,
                                              @PathVariable(name = "location") String location,
                                              Authentication authentication) {
        Profile profile = profileRepository.findByLocation(location).get();
        if(profileService.checkUser(authentication, profile)){
            List<Experience> experiences = experienceService.updateExperience(experienceRequest, experienceId, profile);
            return ResponseEntity.ok().body(new ResponseMessage("Success", experiences));
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/{location}/delete/{experienceId}")
    public ResponseEntity<?> deleteExperience(@RequestBody ExperienceRequest experienceRequest,
                                              @PathVariable(name = "experienceId") Long experienceId,
                                              @PathVariable(name = "location") String location,
                                              Authentication authentication) {
        Profile profile = profileRepository.findByLocation(location).get();

        if(profileService.checkUser(authentication, profile)){
            experienceService.deleteExperience(experienceId, profile);
            return ResponseEntity.ok().body(new ResponseMessage("Success"));
        }else {
            return ResponseEntity.status(401).build();
        }

    }
}
