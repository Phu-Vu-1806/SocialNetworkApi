package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.Skill;
import com.springboot.socialnetworkapi.payload.request.SkillRequest;

import java.util.List;

public interface SkillService {
    List<Skill> addSkill(SkillRequest skillRequest, Profile profile);
    List<Skill> updateSkill(SkillRequest skillRequest,Long skillId, Profile profile);
    void deleteSkill(Long skillId, Profile profile);
}
