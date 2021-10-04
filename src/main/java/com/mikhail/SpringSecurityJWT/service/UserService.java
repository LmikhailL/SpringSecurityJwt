package com.mikhail.SpringSecurityJWT.service;

import com.mikhail.SpringSecurityJWT.entity.RoleEntity;
import com.mikhail.SpringSecurityJWT.entity.UserEntity;
import com.mikhail.SpringSecurityJWT.repository.RoleEntityRepository;
import com.mikhail.SpringSecurityJWT.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private RoleEntityRepository roleEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void saveUser(UserEntity userEntity) {
        RoleEntity userRole = roleEntityRepository.findByName("ROLE_USER");
        userEntity.setRoleEntity(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntityRepository.save(userEntity);
    }

    public UserEntity findByLogin(String login) {
        return userEntityRepository.findByLogin(login);
    }

    public Optional<UserEntity> findByLoginAndPassword(String login, String password) {
        Optional<UserEntity> userEntity = Optional.ofNullable(findByLogin(login));
        if (userEntity.isPresent()) {
            if (passwordEncoder.matches(password, userEntity.get().getPassword())) {
                return userEntity;
            }
        }
        return Optional.empty();
    }

}
