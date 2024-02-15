package com.springboot.socialnetworkapi.service.impl;

import com.springboot.socialnetworkapi.entity.RefreshToken;
import com.springboot.socialnetworkapi.entity.User;
import com.springboot.socialnetworkapi.repository.RefreshTokenRepository;
import com.springboot.socialnetworkapi.repository.UserRepository;
import com.springboot.socialnetworkapi.service.inf.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    public static final int refreshToken_validity = 5*60*60;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RefreshToken createRefreshToken(String username) {

        User user = userRepository.findByUsername(username).get();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshToken_validity));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token was expired. Please make a new signIn request");
        }
        return refreshToken;
    }
}
