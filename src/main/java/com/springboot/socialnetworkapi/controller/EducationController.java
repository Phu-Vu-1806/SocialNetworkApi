package com.springboot.socialnetworkapi.controller;

import com.springboot.socialnetworkapi.entity.Education;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.Skill;
import com.springboot.socialnetworkapi.payload.request.EducationRequest;
import com.springboot.socialnetworkapi.repository.ProfileRepository;
import com.springboot.socialnetworkapi.security.service.UserDetailsImpl;
import com.springboot.socialnetworkapi.service.impl.EducationServiceImpl;
import com.springboot.socialnetworkapi.service.impl.ProfileServiceImpl;
import com.springboot.socialnetworkapi.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education")
public class EducationController {

    @Autowired
    private EducationServiceImpl educationService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileServiceImpl profileService;

    @PostMapping("/{location}/get")
    public ResponseEntity<?> getByProfile(@PathVariable(name = "location") String location) {
        Profile profile = profileRepository.findByLocation(location).get();
        List<Education> educations = profile.getEducations();

        return ResponseEntity.ok().body(new ResponseMessage("Success", educations));
    }

    @PostMapping("/{location}/add")
    public ResponseEntity<?> addEducation(@RequestBody EducationRequest educationRequest,
                                          @PathVariable(name = "location") String location,
                                          Authentication authentication) {

        Profile profile = profileRepository.findByLocation(location).get();
        if (profileService.checkUser(authentication, profile)) {
            List<Education> educations = educationService.addEducation(educationRequest, profile);
            return ResponseEntity.ok().body(new ResponseMessage("Success", educations));
        } else {
            return ResponseEntity.status(401).build();
        }

    }

    @PostMapping("/{location}/update/{id}")
    public ResponseEntity<?> updateEducation(@RequestBody EducationRequest educationRequest,
                                             @PathVariable(name = "id") Long educationId,
                                             @PathVariable(name = "location") String location,
                                             Authentication authentication) {

        Profile profile = profileRepository.findByLocation(location).get();

        if (profileService.checkUser(authentication, profile)) {
            List<Education> educations = educationService.updateEducation(educationRequest, educationId, profile);
            return ResponseEntity.ok().body(new ResponseMessage("Success", educations));
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/{location}/delete/{id}")
    public ResponseEntity<?> deleteEducation(@PathVariable(name = "id") Long educationId,
                                             @PathVariable(name = "location") String location,
                                             Authentication authentication) {

        Profile profile = profileRepository.findByLocation(location).get();
        if (profileService.checkUser(authentication, profile)) {
            educationService.deleteEducation(educationId, profile);
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.status(401).build();
        }

    }
}
