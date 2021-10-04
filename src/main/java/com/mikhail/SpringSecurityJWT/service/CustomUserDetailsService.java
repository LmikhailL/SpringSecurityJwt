package com.mikhail.SpringSecurityJWT.service;

import com.mikhail.SpringSecurityJWT.config.CustomUserDetails;
import com.mikhail.SpringSecurityJWT.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByLogin(login);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }

}
