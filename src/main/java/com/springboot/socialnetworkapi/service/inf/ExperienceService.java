package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.Experience;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.payload.request.ExperienceRequest;

import java.util.List;

public interface ExperienceService {
    List<Experience> addExperience(ExperienceRequest experienceRequest, Profile profile);
    List<Experience> updateExperience(ExperienceRequest experienceRequest, Long experienceId, Profile profile);
    void deleteExperience(Long experienceId, Profile profile);

}
