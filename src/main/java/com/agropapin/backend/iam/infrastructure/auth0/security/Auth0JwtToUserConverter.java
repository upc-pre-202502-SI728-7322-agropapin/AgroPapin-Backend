package com.agropapin.backend.iam.infrastructure.auth0.security;

import com.agropapin.backend.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.stream.Collectors;

public class Auth0JwtToUserConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String ROLES_CLAIM = "https://agropapin-api/roles";
    private static final String EMAIL_CLAIM = "https://agropapin-api/email";
    private static final String PREFIX = "ROLE_";

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {

        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);

        String email = jwt.getClaimAsString(EMAIL_CLAIM);

        UserDetailsImpl userDetails = new UserDetailsImpl(email, authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, jwt, authorities);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        // Asumiendo que el claim de roles es una lista de strings
        if (jwt.hasClaim(ROLES_CLAIM)) {
            return ((Collection<String>) jwt.getClaim(ROLES_CLAIM)).stream()
                    .map(role -> new SimpleGrantedAuthority(PREFIX + role))
                    .collect(Collectors.toList());
        }
        return new java.util.ArrayList<>();
    }
}
