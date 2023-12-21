package com.magicpost.app.magicPost.security.auth;

import com.magicpost.app.magicPost.security.user.UserDetailsImpl;
import com.magicpost.app.magicPost.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.expirationMs}")
    private Integer jwtExpirationMs;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // * Generate accessToken
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        SimpleGrantedAuthority role = (SimpleGrantedAuthority) user.getAuthorities().toArray()[0];
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .claim("email", user.getUsername())
                .claim("role", role.getAuthority())
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new AuthenticationResponse(userDetails.getUser().getUsername(), jwt.getTokenValue());
    }
}

