package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.Social;
import com.springboot.socialnetworkapi.payload.request.SocialRequest;
import com.springboot.socialnetworkapi.repository.SocialRepository;
import com.springboot.socialnetworkapi.service.inf.SocialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialServiceImpl implements SocialService {

    @Autowired
    private SocialRepository socialRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Social addSocial(SocialRequest socialRequest, Profile profile) {
        Social social = modelMapper.map(socialRequest, Social.class);
        social.setProfile(profile);
        socialRepository.save(social);
        return social;
    }

    @Override
    public Social updateSocial(SocialRequest socialRequest, Profile profile){
//        Social social = socialRepository.getSocialByProfile(profile).get();
        Social social = profile.getSocial();

        social.setFacebook(socialRequest.getFacebook());
        social.setInstagram(socialRequest.getInstagram());
        social.setLinkedin(socialRequest.getLinkedin());
        social.setTwitter(socialRequest.getTwitter());
        social.setYoutube(socialRequest.getYoutube());
        profile.setSocial(social);
        socialRepository.save(social);
        return social;
    }


    @Override
    public void deleteSocial(Profile profile) {
        Social social = profile.getSocial();
        socialRepository.delete(social);
    }
}
