package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.Group;
import com.springboot.socialnetworkapi.entity.MemberRequest;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.payload.response.MemberResponse;
import org.springframework.security.core.Authentication;

public interface MemberService {
    MemberResponse joinGroup(Profile profile, Group group);
    void cancelRequest(Profile profile, Group group);
}
