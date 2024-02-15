package com.springboot.socialnetworkapi.controller;

import com.springboot.socialnetworkapi.entity.*;
import com.springboot.socialnetworkapi.entity.enums.ERoleGroup;
import com.springboot.socialnetworkapi.payload.request.CreateGroup;
import com.springboot.socialnetworkapi.payload.request.PostRequest;
import com.springboot.socialnetworkapi.payload.response.*;
import com.springboot.socialnetworkapi.repository.GroupRepository;
import com.springboot.socialnetworkapi.repository.ManagerRepository;
import com.springboot.socialnetworkapi.repository.ProfileRepository;
import com.springboot.socialnetworkapi.security.service.UserDetailsImpl;
import com.springboot.socialnetworkapi.service.impl.GroupServiceImpl;
import com.springboot.socialnetworkapi.service.impl.MemberServiceImpl;
import com.springboot.socialnetworkapi.service.impl.PostServiceImpl;
import com.springboot.socialnetworkapi.service.impl.ProfileServiceImpl;
import com.springboot.socialnetworkapi.utils.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private ProfileServiceImpl profileService;

    @Autowired
    private GroupServiceImpl groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MemberServiceImpl memberService;

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{locationGroup}")
    public ResponseEntity<?> group(@RequestParam(defaultValue = "0") int pageNo,
                                   @RequestParam(defaultValue = "10") int pageSize,
                                   @RequestParam(defaultValue = "date") String sortBy,
                                   @PathVariable(name = "locationGroup") String locationGroup) {
        Group group = groupRepository.findByLocation(locationGroup)
                .orElseThrow(() -> new RuntimeException("Not found location for group"));
        List<Post> posts = postService.getAllPostFromGroup(group, pageNo, pageSize, sortBy);

        if (posts.isEmpty()) {
            return ResponseEntity.ok().body(new ResponseMessage("There are no posts in this group yet"));
        } else {
            List<PostOfGroup> post = posts.stream().map(
                    post1 -> {
                        PostOfGroup postOfGroup = modelMapper.map(post1, PostOfGroup.class);
                        postOfGroup.setNameProfileUser(post1.getOwner().getUser().getName());
                        postOfGroup.setLocationProfile(post1.getOwner().getLocation());
                        return postOfGroup;
                    }
            ).collect(Collectors.toList());
            return ResponseEntity.ok().body(new ObjectResponse(group.getName(), group.getLocation(), post));
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody CreateGroup createGroup, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Profile profile = profileService.getProfileByUsername(userDetails.getUsername());

        GroupResponse groupResponse = groupService.createGroup(profile, createGroup);

        return ResponseEntity.ok().body(new ObjectResponse(profile.getUser().getName(),
                profile.getLocation(), groupResponse));
    }

    @DeleteMapping("/{locationGroup}/delete")
    public ResponseEntity<?> deleteGroup(Authentication authentication,
                                         @PathVariable(name = "locationGroup") String locationGroup) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Profile profile = profileService.getProfileByUsername(userDetails.getUsername());

        Group group = groupRepository.findByLocation(locationGroup)
                .orElseThrow(() -> new RuntimeException("Not found location for group"));
//       ở đây chúng ta sử dụng == thay vi equals bởi đây là so sánh tham chiếu
        if (group.getProfile() == profile) {
            groupRepository.delete(group);
            return ResponseEntity.ok().body("Success");
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/{locationGroup}/edit_name={name}")
    public ResponseEntity<?> editName(@PathVariable(name = "name") String name,
                                      @PathVariable(name = "locationGroup") String locationGroup) {
        Group group = groupRepository.findByLocation(locationGroup)
                .orElseThrow(() -> new RuntimeException("Not found location for group"));
        ;
        group.setName(name);

        return ResponseEntity.ok().body("Success");
    }

    @PutMapping("/{locationGroup}/edit_location={location}")
    public ResponseEntity<?> editLocation(@PathVariable(name = "location") String location,
                                          @PathVariable(name = "locationGroup") String locationGroup) {
        Group group = groupRepository.findByLocation(locationGroup)
                .orElseThrow(() -> new RuntimeException("Not found location for group"));
        group.setLocation(location);

        return ResponseEntity.ok().body("Success");
    }

    @PostMapping("/{locationGroup}/add/manager/{locationProfile}")
    public ResponseEntity<?> addManager(Authentication authentication,
                                        @PathVariable(name = "locationProfile") String locationProfile,
                                        @PathVariable(name = "locationGroup") String locationGroup) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Profile profile = profileService.getProfileByUsername(userDetails.getUsername());
        Profile addManager = profileRepository.findByLocation(locationProfile)
                .orElseThrow(() -> new RuntimeException("Not found location for profile"));
        ;
        Group group = groupRepository.findByLocation(locationGroup)
                .orElseThrow(() -> new RuntimeException("Not found location for group"));
        ;
//       ở đây chúng ta sử dụng == thay vi equals bởi đây là so sánh tham chiếu
        if (group.getProfile() == profile) {
            Manager manager = new Manager(profile, ERoleGroup.MODERATOR_GROUP, new Date(), group);
            managerRepository.save(manager);
            return ResponseEntity.ok().body("Success");
        }

        return null;
    }

    @GetMapping("/{locationGroup}/joinGroup")
    public ResponseEntity<?> joinGroup(Authentication authentication,
                                       @PathVariable(name = "locationGroup") String locationGroup) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Profile profile = profileService.getProfileByUsername(userDetails.getUsername());

        Group group = groupRepository.findByLocation(locationGroup)
                .orElseThrow(() -> new RuntimeException("Not found location for group"));
        MemberResponse memberResponse = memberService.joinGroup(profile, group);

        return ResponseEntity.ok().body(new ResponseMessage("Success", memberResponse));
    }

    @DeleteMapping("/{locationGroup}/cancel_request")
    public ResponseEntity<?> cancelRequest(Authentication authentication,
                                           @PathVariable(name = "locationGroup") String locationGroup) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Profile profile = profileService.getProfileByUsername(userDetails.getUsername());

        Group group = groupRepository.findByLocation(locationGroup)
                .orElseThrow(() -> new RuntimeException("Not found location for group"));
        memberService.cancelRequest(profile, group);

        return ResponseEntity.ok().body(new ResponseMessage("Success"));
    }

    @GetMapping("/{locationGroup}/confirm")
    public ResponseEntity<?> confirmRequest(Authentication authentication,
                                            @PathVariable(name = "locationGroup") String locationGroup) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Profile profile = profileService.getProfileByUsername(userDetails.getUsername());

        Group group = groupRepository.findByLocation(locationGroup)
                .orElseThrow(() -> new RuntimeException("Not found location for group"));
        List<Manager> managers = group.getManagers();
        boolean check = false;
        for (Manager manager : managers) {
            if (manager.getProfile() == profile) {
                check = true;
            }
        }
        if (!check) {
            return ResponseEntity.status(401).build();
        }
        MemberResponse memberResponse = memberService.confirmMember(profile, group);
        return ResponseEntity.ok().body(new ResponseMessage("Success", memberResponse));
    }

    @PostMapping("/{locationGroup}/create_post")
    public ResponseEntity<?> createPostForGroup(@RequestBody PostRequest postRequest,
                                                @PathVariable(name = "locationGroup") String locationGroup,
                                                Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Profile profile = profileService.getProfileByUsername(userDetails.getUsername());

        Group group = groupRepository.findByLocation(locationGroup)
                .orElseThrow(() -> new RuntimeException("Not found location for group"));

        List<Member> members = profile.getMembers();

        PostResponse postResponse = null;
        for (Member member : members) {
            if (member.getGroup() == group) {
                postResponse = postService.createPost(postRequest, profile, group);
                return ResponseEntity.ok().body(new ResponseMessage("Success", postResponse));
            }
        }
        return ResponseEntity.status(401).build();
    }
}
