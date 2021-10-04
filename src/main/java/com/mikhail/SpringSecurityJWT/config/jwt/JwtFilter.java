package com.mikhail.SpringSecurityJWT.config.jwt;

import com.mikhail.SpringSecurityJWT.config.CustomUserDetails;
import com.mikhail.SpringSecurityJWT.service.CustomUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

import static io.jsonwebtoken.lang.Strings.hasText;

@Component
@Log4j2
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.log(Level.INFO, "Applied filter...");
        Optional<String> token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token.isPresent() && jwtProvider.isTokenValid(token.get())) {
            String userLogin = jwtProvider.getLoginFromToken(token.get());
            CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userLogin);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            customUserDetails,
                            Optional.empty(),
                            customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        Optional<String> bearer = Optional.ofNullable(request.getHeader(AUTHORIZATION));
        if (bearer.isPresent() && hasText(bearer.get()) && bearer.get().startsWith("Bearer ")) {
            return Optional.of(bearer.get().substring(7));

        } else {
            log.log(Level.INFO, "Smt went wrong..." +
                    "(Register(doesn't even have it) and Auth(it only returns token) " +
                    "don't have token, so we can't get login from token)");
            return Optional.empty();

        }

    }

}
