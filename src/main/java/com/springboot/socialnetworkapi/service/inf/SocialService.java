package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.Social;
import com.springboot.socialnetworkapi.payload.request.SocialRequest;

public interface SocialService {

    Social addSocial(SocialRequest socialRequest, Profile profile);
    Social updateSocial(SocialRequest socialRequest, Profile profile);
    void deleteSocial(Profile profile);

}
