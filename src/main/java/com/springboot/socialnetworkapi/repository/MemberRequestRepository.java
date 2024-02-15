package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.MemberRequest;
import com.springboot.socialnetworkapi.entity.Profile;
import org.springframework.data.repository.CrudRepository;

public interface MemberRequestRepository extends CrudRepository<MemberRequest, Long> {
    MemberRequest findByProfile(Profile profile);
}
