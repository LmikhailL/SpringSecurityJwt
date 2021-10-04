package com.mikhail.SpringSecurityJWT.repository;

import com.mikhail.SpringSecurityJWT.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByLogin(String login);

}
