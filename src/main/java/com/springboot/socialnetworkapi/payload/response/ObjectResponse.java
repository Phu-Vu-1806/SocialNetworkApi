package com.springboot.socialnetworkapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ObjectResponse {
    private String nameProfile;

    private String locationProfile;

    private Object postResponse;
}
