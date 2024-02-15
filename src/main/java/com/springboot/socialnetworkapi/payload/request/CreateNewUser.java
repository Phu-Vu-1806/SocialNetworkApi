package com.springboot.socialnetworkapi.payload.request;

import com.springboot.socialnetworkapi.entity.Profile;
import lombok.Data;

import java.util.Set;

@Data
public class CreateNewUser {

    private String name;
    private String username;
    private String email;
    private String password;
    private Set<String> role;
    private Profile profile;
}
