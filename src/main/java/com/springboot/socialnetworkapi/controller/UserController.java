package com.springboot.socialnetworkapi.controller;

import com.springboot.socialnetworkapi.entity.User;
import com.springboot.socialnetworkapi.payload.request.ChangeName;
import com.springboot.socialnetworkapi.payload.request.PasswordRequest;
import com.springboot.socialnetworkapi.payload.response.UserResponse;
import com.springboot.socialnetworkapi.repository.UserRepository;
import com.springboot.socialnetworkapi.security.service.UserDetailsImpl;
import com.springboot.socialnetworkapi.service.impl.UserServiceImpl;
import com.springboot.socialnetworkapi.service.inf.UserService;
import com.springboot.socialnetworkapi.utils.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/me")
    public ResponseEntity<?> getUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(userResponse));
    }

    @GetMapping("/search/name")
    public ResponseEntity<?> getAllUserByUsername(@RequestParam(defaultValue = "0") int pageNo,
                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                  @RequestParam(defaultValue = "keyword") String keyword) {

        List<User> users = userService.getAllUserByFilter(keyword, pageNo, pageSize, sortBy);
        List<UserResponse> userResponses = users.stream().map(user -> modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(userResponses);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseMessage> changePassword(@RequestBody PasswordRequest passwordRequest, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(userDetails);
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());

        if (user.isPresent()) {

            boolean isPasswordCorrect = passwordEncoder.matches(passwordRequest.getOldPassword(), user.get().getPassword());
            if (isPasswordCorrect) {
                String encodedPassword = passwordEncoder.encode(passwordRequest.getNewPassword());
                user.get().setPassword(encodedPassword);
                userRepository.save(user.get());

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Change Password Successfully"));
            }

        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/change-name")
    public ResponseEntity<ResponseMessage> changeName(@RequestBody ChangeName changeName, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(userDetails);
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());

        if (user.isPresent()) {
            user.get().setName(changeName.getName());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Change Name Successfully"));
        }

        return ResponseEntity.status(404).build();
    }
}
