package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String accessToken);
}
