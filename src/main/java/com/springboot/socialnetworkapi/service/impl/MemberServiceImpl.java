package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.*;
import com.springboot.socialnetworkapi.payload.response.MemberResponse;
import com.springboot.socialnetworkapi.repository.MemberRepository;
import com.springboot.socialnetworkapi.repository.MemberRequestRepository;
import com.springboot.socialnetworkapi.service.inf.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRequestRepository memberRequestRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public MemberResponse joinGroup(Profile profile, Group group) {
//check member đã join group chưa?
//        List<Member> members = profile.getMembers();
//        List<Member> collect = members.stream().filter(member -> member.getProfile() == (profile))
//                                .collect(Collectors.toList());
//        if(collect.isEmpty()){
//            MemberRequest memberRequest = new MemberRequest(new Date(), profile, group);
//            MemberResponse memberResponse = modelMapper.map(memberRequest, MemberResponse.class);
//            memberRequestRepository.save(memberRequest);
//            return memberResponse;
//        }else {
//            return null;
//        }
        List<Member> members = profile.getMembers();
        for (Member member : members) {
            if (member.getGroup() == group) {
                throw new RuntimeException("you are already a member of this group");
            }
        }
        List<MemberRequest> memberRequests = profile.getMemberRequests();
        for(MemberRequest memberRequest : memberRequests){
            if(memberRequest.getGroup() == group){
                throw new RuntimeException("You have sent a request to this group");
            }
        }
        MemberRequest memberRequest = new MemberRequest(new Date(), profile, group);
        MemberResponse memberResponse = modelMapper.map(memberRequest, MemberResponse.class);
//        memberResponse.setName(profile.getUser().getName());
        memberRequestRepository.save(memberRequest);
        return memberResponse;

    }

    @Override
    public void cancelRequest(Profile profile, Group group) {
        List<MemberRequest> memberRequests = profile.getMemberRequests();

        for (MemberRequest memberRequest : memberRequests) {
            if (memberRequest.getGroup() == group) {
                memberRequestRepository.delete(memberRequest);
            }
        }
    }

    public MemberResponse confirmMember(Profile profile, Group group){
        List<Member> members = profile.getMembers();
        for (Member member : members) {
            if (member.getGroup() == group) {
                throw new RuntimeException("you are already a member of this group");
            }
        }

//        List<Manager> managers = group.getManagers();
//        boolean check = false;
//        for (Manager manager : managers){
//            if(manager.getProfile() == profile){
//                check = true;
//            }
//        }

        MemberResponse memberResponse = null;
        List<MemberRequest> memberRequests = profile.getMemberRequests();
        for(MemberRequest memberRequest : memberRequests){
            if(memberRequest.getGroup() == group){
                Member member = modelMapper.map(memberRequest, Member.class);
                memberResponse = modelMapper.map(memberRequest, MemberResponse.class);
                memberRepository.save(member);

                cancelRequest(profile, group);
            }
        }
        return memberResponse;
//        return null;
    }
}
