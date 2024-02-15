package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.Education;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.payload.request.EducationRequest;
import com.springboot.socialnetworkapi.repository.EducationRepository;
import com.springboot.socialnetworkapi.service.inf.EducationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Education> addEducation(EducationRequest educationRequest, Profile profile) {
        List<Education> educations = profile.getEducations();
        Education education = modelMapper.map(educationRequest, Education.class);
        education.setProfile(profile);
        educations.add(education);
        educationRepository.save(education);
        return educations;
    }

    @Override
    public List<Education> updateEducation(EducationRequest educationRequest,Long educationId, Profile profile) {

        List<Education> educations = profile.getEducations();
        boolean check = false;
        for(Education education : educations){
            if(education.getId() == educationId){
                check = true;
                education.setDescription(education.getDescription());
                education.setFrom(education.getFrom());
                education.setFieldOfStudy(education.getFieldOfStudy());
                education.setSchoolName(education.getSchoolName());
                educationRepository.save(education);
            }
        }
        if (!check){
            throw new RuntimeException("Not found id for education");
        }
        return educations;
    }

    @Override
    public void deleteEducation(Long educationId, Profile profile) {
        List<Education> educations = profile.getEducations();
        Iterator<Education> iterator = educations.iterator();
        boolean check = false;
        while (iterator.hasNext()){
            Education education = iterator.next();
            if(education.getId() == educationId){
                check = true;
                iterator.remove();
                educationRepository.delete(education);
            }
        }
        if (!check){
            throw new RuntimeException("Not found id for education");
        }
    }
}
