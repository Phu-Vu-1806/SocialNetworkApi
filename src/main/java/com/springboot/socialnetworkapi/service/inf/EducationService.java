package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.Education;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.payload.request.EducationRequest;

import java.util.List;

public interface EducationService {
    List<Education> addEducation(EducationRequest educationRequest, Profile profile);
    List<Education> updateEducation(EducationRequest educationRequest,Long educationId, Profile profile);
    void deleteEducation(Long educationId, Profile profile);
}
