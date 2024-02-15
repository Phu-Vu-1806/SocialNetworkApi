package com.springboot.socialnetworkapi.payload.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceRequest {

    private String title;

    private String companyName;

    private String location;

    private Date from;

    private Date to;

    private String description;
}
