package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.Social;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialRepository extends JpaRepository<Social, Long> {
    Optional<Social> getSocialByProfile(Profile profile);
}
