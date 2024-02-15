package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.Experience;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.payload.request.ExperienceRequest;
import com.springboot.socialnetworkapi.repository.ExperienceRepository;
import com.springboot.socialnetworkapi.service.inf.ExperienceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Experience> addExperience(ExperienceRequest experienceRequest, Profile profile) {
        List<Experience> experiences = profile.getExperiences();
        Experience experience = modelMapper.map(experienceRequest, Experience.class);
        experience.setProfile(profile);
        experiences.add(experience);
        experienceRepository.save(experience);
        return experiences;
    }

    @Override
    public List<Experience> updateExperience(ExperienceRequest experienceRequest, Long experienceId, Profile profile) {

        List<Experience> experiences = profile.getExperiences();
        boolean check = false;
        for(Experience experience : experiences){
            if(experience.getId() == experienceId){
                check = true;
                experience.setTitle(experienceRequest.getTitle());
                experience.setCompanyName(experienceRequest.getCompanyName());
                experience.setDescription(experienceRequest.getDescription());
                experience.setFrom(experienceRequest.getFrom());
                experience.setTo(experienceRequest.getTo());
                experience.setLocation(experienceRequest.getLocation());
//                System.out.println(experience.toString());
                experienceRepository.save(experience);
            }
        }
        if (!check){
            throw new RuntimeException("Not found id for education");
        }
        return experiences;
    }

    @Override
    public void deleteExperience(Long experienceId, Profile profile) {

        List<Experience> experiences = profile.getExperiences();
        Iterator<Experience> iterator = experiences.iterator();
        boolean check = false;
        while (iterator.hasNext()){
            Experience experience = iterator.next();
            if(experience.getId() == experienceId){
                check = true;
                iterator.remove();
                experienceRepository.delete(experience);
            }
        }
        if (!check){
            throw new RuntimeException("Not found id for education");
        }
    }
}
