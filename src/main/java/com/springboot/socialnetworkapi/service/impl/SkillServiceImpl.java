package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.Education;
import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.Skill;
import com.springboot.socialnetworkapi.payload.request.SkillRequest;
import com.springboot.socialnetworkapi.repository.SkillRepository;
import com.springboot.socialnetworkapi.service.inf.SkillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Skill> addSkill(SkillRequest skillRequest, Profile profile) {
        List<Skill> skills = profile.getSkills();
        Skill skill = modelMapper.map(skillRequest, Skill.class);
        skill.setName(skillRequest.getName());
        skills.add(skill);
        skillRepository.save(skill);
        return skills;
    }

    @Override
    public List<Skill> updateSkill(SkillRequest skillRequest, Long skillId, Profile profile) {
        List<Skill> skills = profile.getSkills();
        boolean check = false;
        for(Skill skill : skills){
            if(skill.getId() == skillId){
                check = true;
                skill.setName(skillRequest.getName());
                skillRepository.save(skill);
            }
        }
        if (!check){
            throw new RuntimeException("Not found id for education");
        }
        return skills;
    }

    @Override
    public void deleteSkill(Long skillId, Profile profile) {

        List<Skill> skills = profile.getSkills();
        Iterator<Skill> iterator = skills.iterator();
        boolean check = false;
        while (iterator.hasNext()){
            Skill skill = iterator.next();
            if(skill.getId() == skillId){
                check = true;
                iterator.remove();
                skillRepository.delete(skill);
            }
        }
        if (!check){
            throw new RuntimeException("Not found id for education");
        }
    }
}
