package com.springboot.socialnetworkapi.controller;

import com.springboot.socialnetworkapi.entity.*;
import com.springboot.socialnetworkapi.payload.request.CreateNewUser;
import com.springboot.socialnetworkapi.payload.request.LoginRequest;
import com.springboot.socialnetworkapi.payload.request.RefreshTokenRequest;
import com.springboot.socialnetworkapi.payload.response.LoginResponse;
import com.springboot.socialnetworkapi.payload.response.TokenRefreshResponse;
import com.springboot.socialnetworkapi.repository.RefreshTokenRepository;
import com.springboot.socialnetworkapi.repository.RoleRepository;
import com.springboot.socialnetworkapi.repository.UserRepository;
import com.springboot.socialnetworkapi.security.jwt.JwtToken;
import com.springboot.socialnetworkapi.security.service.UserDetailsImpl;
import com.springboot.socialnetworkapi.security.service.UserDetailsServiceImpl;
import com.springboot.socialnetworkapi.service.impl.ProfileServiceImpl;
import com.springboot.socialnetworkapi.service.impl.UserServiceImpl;
import com.springboot.socialnetworkapi.service.inf.RefreshTokenService;
import com.springboot.socialnetworkapi.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ProfileServiceImpl profileService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody CreateNewUser createNewUser){
        if(userRepository.existsByEmail(createNewUser.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Error: Email "+createNewUser.getEmail()+" is already in use"));
        }

        if(userRepository.existsByUsername(createNewUser.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("Error: User "+createNewUser.getUsername()+" is already in use"));
        }

        Profile profile = profileService.createProfile();
        createNewUser.setProfile(profile);
        userService.createUser(createNewUser);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
//        xác thực người dùng và lưu người dùng trong thread-local bởi SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final String token = jwtToken.doGenerateToken(userDetails.getUsername());
        refreshTokenService.createRefreshToken(userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Login successful"
                , new LoginResponse(
                        token,
                        userDetails.getName(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles)
        ));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        String refreshToken = refreshTokenRequest.getRefreshToken();
        return refreshTokenRepository.findByToken(refreshTokenRequest.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtToken.generateTokenFromUsername(user);
                    return ResponseEntity.ok(new TokenRefreshResponse(accessToken, refreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found in database"));
    }
}
