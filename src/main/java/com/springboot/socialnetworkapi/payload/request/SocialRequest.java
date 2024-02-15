package com.springboot.socialnetworkapi.payload.request;

import lombok.Data;

@Data
public class SocialRequest {
    private String youtube;
    private String facebook;
    private String twitter;
    private String instagram;
    private String linkedin;
}
