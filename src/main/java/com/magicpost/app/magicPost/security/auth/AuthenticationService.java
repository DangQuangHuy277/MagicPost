package com.magicpost.app.magicPost.security.auth;

import com.magicpost.app.magicPost.security.user.UserDetailsImpl;
import com.magicpost.app.magicPost.user.UserRepository;
import com.magicpost.app.magicPost.user.entity.leader.CompanyLeader;
import com.magicpost.app.magicPost.user.entity.leader.GatheringLeader;
import com.magicpost.app.magicPost.user.entity.leader.TransactionLeader;
import com.magicpost.app.magicPost.user.entity.staff.GatheringStaff;
import com.magicpost.app.magicPost.user.entity.staff.TransactionStaff;
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
        JwtClaimsSet.Builder jwtClaimsSetBuilder = JwtClaimsSet.builder()
                .claim("email", user.getUsername())
                .claim("scope", role.getAuthority())
                .claim("phone", user.getUser().getPhone());
        Long pointId = switch (user.getUser()) {
            case GatheringLeader gl -> gl.getGatheringPoint().getId();
            case GatheringStaff gs -> gs.getGatheringPoint().getId();
            case TransactionLeader tl -> tl.getTransactionPoint().getId();
            case TransactionStaff ts -> ts.getTransactionPoint().getId();
            case CompanyLeader cl -> -1L;
            default -> throw new IllegalStateException("Unexpected value: " + user.getUser());
        };
        if (!pointId.equals(-1L)) jwtClaimsSetBuilder.claim("pointId", pointId);
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSetBuilder.build());
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new AuthenticationResponse(userDetails.getUser().getUsername(), jwt.getTokenValue());
    }
}

