package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.Group;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.payload.request.CreateGroup;
import com.springboot.socialnetworkapi.payload.response.GroupResponse;

public interface GroupService {
    GroupResponse createGroup(Profile profile, CreateGroup createGroup);
    void deleteGroup();
}
