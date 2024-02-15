package com.springboot.socialnetworkapi.service.inf;

import com.springboot.socialnetworkapi.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username);
    RefreshToken verifyExpiration(RefreshToken refreshToken);
}
