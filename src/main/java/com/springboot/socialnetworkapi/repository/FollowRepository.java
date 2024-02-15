package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.Follow;
import com.springboot.socialnetworkapi.entity.Profile;
import org.springframework.data.repository.CrudRepository;

public interface FollowRepository extends CrudRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(Profile follower, Profile following);
}
