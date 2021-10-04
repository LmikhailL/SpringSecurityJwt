package com.mikhail.SpringSecurityJWT.repository;

import com.mikhail.SpringSecurityJWT.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByName(String name);

}
