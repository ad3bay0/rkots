package com.ad3bay0.rkots.repository;

import java.util.Optional;
import java.util.UUID;

import com.ad3bay0.rkots.enums.RoleEnum;
import com.ad3bay0.rkots.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByRoleName(RoleEnum roleName);
    
}