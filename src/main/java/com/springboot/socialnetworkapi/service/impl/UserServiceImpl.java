package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.enums.ERole;
import com.springboot.socialnetworkapi.entity.Role;
import com.springboot.socialnetworkapi.entity.User;
import com.springboot.socialnetworkapi.payload.request.CreateNewUser;
import com.springboot.socialnetworkapi.repository.RoleRepository;
import com.springboot.socialnetworkapi.repository.UserRepository;
import com.springboot.socialnetworkapi.service.inf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User createUser(CreateNewUser createNewUser) {

        String password = passwordEncoder.encode(createNewUser.getPassword());
        User user = User.builder()
                .name(createNewUser.getName())
                .username(createNewUser.getUsername())
                .email(createNewUser.getEmail())
                .password(password)
                .profile(createNewUser.getProfile())
                .build();

        Set<String> strRoles = createNewUser.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "mod":
                        Role modRole = roleRepository.findByRole(ERole.MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    case "admin":
                        Role adminRole = roleRepository.findByRole(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRole(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAllUserByFilter(String keyword, Integer pageNo, Integer pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> userPage = userRepository.getUserByUsername(keyword, pageable);
        if (userPage.hasContent()) {
            return userPage.getContent();
        } else {
            return new ArrayList<User>();
        }
    }


}
