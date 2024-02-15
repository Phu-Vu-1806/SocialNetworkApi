package com.springboot.socialnetworkapi.payload.request;

import lombok.Data;

@Data
public class CreateNewProfile {
    private String website;
    private String location;
    private String joinDate;
}
