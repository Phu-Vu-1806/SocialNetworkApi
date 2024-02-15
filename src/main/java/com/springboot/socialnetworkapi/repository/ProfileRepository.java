package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.Profile;
import com.springboot.socialnetworkapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsByLocation(String location);
    Optional<Profile> findByUser(User user);
    Optional<Profile> findByLocation(String location);
}
