package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.Group;
import com.springboot.socialnetworkapi.entity.Member;
import com.springboot.socialnetworkapi.entity.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long> {
    boolean existsByLocation(String location);
    Optional<Group> findByLocation(String location);
}
