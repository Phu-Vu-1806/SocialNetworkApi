package com.springboot.socialnetworkapi.payload.response;

import lombok.Data;

import java.util.Date;

@Data
public class MemberResponse {
    private Date date;

    private String locationProfile;

    private String websiteProfile;

    private String nameProfileUser;
}
