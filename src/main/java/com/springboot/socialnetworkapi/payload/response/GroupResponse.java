package com.springboot.socialnetworkapi.payload.response;

import lombok.Data;

@Data
public class GroupResponse {
    private String name;

    private String location;

    private String website;

    private String description;
}
