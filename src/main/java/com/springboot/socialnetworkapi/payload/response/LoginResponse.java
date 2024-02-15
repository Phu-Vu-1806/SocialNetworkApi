package com.springboot.socialnetworkapi.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {


    private int id;
    private String accessToken;
//    private String refreshToken;
    private String name;
    private String username;
    private String email;

    private String type = "Bearer";

    private List<String> roles;

    public LoginResponse(String accessToken, String name, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.name = name;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
