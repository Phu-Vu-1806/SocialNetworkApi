package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.*;
import com.springboot.socialnetworkapi.entity.enums.ERoleGroup;
import com.springboot.socialnetworkapi.payload.request.CreateGroup;
import com.springboot.socialnetworkapi.payload.response.GroupResponse;
import com.springboot.socialnetworkapi.repository.GroupRepository;
import com.springboot.socialnetworkapi.repository.ManagerRepository;
import com.springboot.socialnetworkapi.repository.MemberRepository;
import com.springboot.socialnetworkapi.service.inf.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public GroupResponse createGroup(Profile profile, CreateGroup createGroup) {

        Group group = modelMapper.map(createGroup, Group.class);
        String location = randomLocation();
        while (groupRepository.existsByLocation(location)){
            location = randomLocation();
        }

        String website = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/group/")
                .path(location)
                .toUriString();
        group.setLocation(location);
        group.setWebsite(website);
        group.setDate(new Date());
        group.setProfile(profile);

        GroupResponse groupResponse = modelMapper.map(group, GroupResponse.class);

        groupRepository.save(group);

        Manager manager = new Manager(profile, ERoleGroup.ADMIN_GROUP, new Date(), group);
        managerRepository.save(manager);
        Member member = new Member(new Date(), profile, group);
        memberRepository.save(member);

        return groupResponse;
    }

    @Override
    public void deleteGroup() {

    }

    public String randomLocation() {
        double randomDouble = Math.random();
        randomDouble = randomDouble * 1000000000 + 100000000;
        int randomInt = (int) randomDouble;
        String location = String.valueOf(randomInt);
        return location;
    }
}
