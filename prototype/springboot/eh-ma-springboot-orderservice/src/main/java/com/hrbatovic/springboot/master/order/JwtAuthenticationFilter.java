package com.hrbatovic.springboot.master.order;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //TODO: configurable maybe
    private static final String SECRET = "8a612b72e7f7159cdc6d09f93397ff56347b71396ff0cd446c1775c991b7654cedf0cbb2a0065e0ba4a8aca66829d0f0bc0a7ae4ffe118284f216e1ed381dbd3";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();

            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            JwtAuthentication jwtAuthentication = new JwtAuthentication(username, authorities);
            jwtAuthentication.setAuthenticated(true);


            jwtAuthentication.setEmail((String)claims.get("email"));
            jwtAuthentication.setFirstName((String) claims.get("given_name"));
            jwtAuthentication.setLastName((String) claims.get("family_name"));
            jwtAuthentication.setSessionId(UUID.fromString((String)claims.get("sid")));
            jwtAuthentication.setUserId(UUID.fromString((String)claims.get("sub")));

            SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
