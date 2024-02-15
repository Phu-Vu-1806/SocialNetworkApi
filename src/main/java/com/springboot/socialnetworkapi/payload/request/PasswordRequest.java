package com.springboot.socialnetworkapi.payload.request;

import lombok.Data;

@Data
public class PasswordRequest {
    private String oldPassword;
    private String newPassword;
}
