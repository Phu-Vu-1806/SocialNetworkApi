package com.springboot.socialnetworkapi.controller;

import com.springboot.socialnetworkapi.entity.Notification;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.repository.ProfileRepository;
import com.springboot.socialnetworkapi.service.inf.NotificationService;
import com.springboot.socialnetworkapi.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/{locationProfile}/getAll")
    public ResponseEntity<?> getAll(@PathVariable(name = "locationProfile") String locationProfile,
                                    @RequestParam(defaultValue = "0") int pageNo,
                                    @RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "id") String sortBy){

        Optional<Profile> profile = profileRepository.findByLocation(locationProfile);
        if(profile.isEmpty()){
            throw new RuntimeException("Not found profile");
        }
        List<Notification> notifications = notificationService.getAll(profile.get().getId(), pageNo, pageSize, sortBy);

        return ResponseEntity.ok().body(new ResponseMessage("success", notifications));
    }
}
