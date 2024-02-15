package com.springboot.socialnetworkapi.payload.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
