package com.springboot.socialnetworkapi.controller;

import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.Skill;
import com.springboot.socialnetworkapi.payload.request.SkillRequest;
import com.springboot.socialnetworkapi.repository.ProfileRepository;
import com.springboot.socialnetworkapi.security.service.UserDetailsImpl;
import com.springboot.socialnetworkapi.service.impl.ProfileServiceImpl;
import com.springboot.socialnetworkapi.service.impl.SkillServiceImpl;
import com.springboot.socialnetworkapi.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SkillServiceImpl skillService;

    @Autowired
    private ProfileServiceImpl profileService;

    @GetMapping("/{location}/get")
    public ResponseEntity<?> getSkill(@PathVariable(name = "location") String location) {
        Profile profile = profileRepository.findByLocation(location).get();
        List<Skill> skills = profile.getSkills();
        return ResponseEntity.ok().body(new ResponseMessage("Success", skills));
    }

    @PostMapping("/{location}/add")
    public ResponseEntity<?> addSkill(@RequestBody SkillRequest skillRequest,
                                      @PathVariable(name = "location") String location,
                                      Authentication authentication) {
        Profile profile = profileRepository.findByLocation(location).get();
        if (profileService.checkUser(authentication, profile)) {
            List<Skill> skills = skillService.addSkill(skillRequest, profile);
            return ResponseEntity.ok().body(new ResponseMessage("Success", skills));
        }else {
            return ResponseEntity.status(401).build();
        }
    }

    @PutMapping("/{location}/update/{experienceId}")
    public ResponseEntity<?> updateExperience(@RequestBody SkillRequest skillRequest,
                                              @PathVariable(name = "experienceId") Long experienceId,
                                              @PathVariable(name = "location") String location,
                                              Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Profile profile = profileRepository.findByLocation(location).get();

        if (profileService.checkUser(authentication, profile)) {
            List<Skill> skills = skillService.updateSkill(skillRequest, experienceId, profile);
            return ResponseEntity.ok().body(new ResponseMessage("Success", skills));
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/{location}/delete/{experienceId}")
    public ResponseEntity<?> deleteExperience(@RequestBody SkillRequest skillRequest,
                                              @PathVariable(name = "experienceId") Long experienceId,
                                              @PathVariable(name = "location") String location,
                                              Authentication authentication) {
        Profile profile = profileRepository.findByLocation(location).get();
        if (profileService.checkUser(authentication, profile)) {
            skillService.deleteSkill(experienceId, profile);
            return ResponseEntity.ok().body(new ResponseMessage("Success"));
        }else {
            return ResponseEntity.status(401).build();
        }
    }
}
