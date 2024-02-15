package com.springboot.socialnetworkapi.payload.request;

import lombok.Data;

import java.util.Date;

@Data
public class CreateGroup {
    private String name;

    private String description;
}
