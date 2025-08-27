package com.dulo.chat_platform.service.impl;

import com.dulo.chat_platform.entity.Role;
import com.dulo.chat_platform.entity.User;
import com.dulo.chat_platform.entity.enums.ErrorEnum;
import com.dulo.chat_platform.exception.AppException;
import com.dulo.chat_platform.repository.UserRepository;
import com.dulo.chat_platform.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl  implements JwtService{

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;

    @Override
    public String generateJwtToken(Authentication authentication){
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email);
        if(user == null) throw new AppException(ErrorEnum.USER_NOT_FOUND);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getEmail())
                .issuedAt(Instant.now())
                .claim("authorities", getAuthorities(user.getRoles()))
                .expiresAt(Instant.now().plus(3, ChronoUnit.DAYS))
                .issuer("DULO_Chat platform")
                .build();
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claims)).getTokenValue();
    }

    private String getAuthorities(Set<Role> roles){
        return roles.stream().map(role -> role.getName().name()).collect(Collectors.joining(" "));
    }
}
