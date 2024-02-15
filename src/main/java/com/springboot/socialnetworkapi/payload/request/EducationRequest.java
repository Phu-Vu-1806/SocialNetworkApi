package com.springboot.socialnetworkapi.payload.request;

import lombok.Data;

import java.util.Date;

@Data
public class EducationRequest {

    private String schoolName;

    private String fieldOfStudy;

    private Date from;

    private Date to;

    private String description;
}
