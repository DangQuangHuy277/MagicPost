package com.magicpost.app.magicPost.security;

import com.magicpost.app.magicPost.user.UserRepository;
import com.magicpost.app.magicPost.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthorization {

    public boolean belongsPoint(Authentication authentication, Long pointId){
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return pointId.equals(jwt.getClaim("pointId"));
    }
}
