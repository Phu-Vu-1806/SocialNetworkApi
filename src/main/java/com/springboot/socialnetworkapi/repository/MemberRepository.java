package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
