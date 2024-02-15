package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.enums.ERole;
import com.springboot.socialnetworkapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
    boolean existsByRole(ERole role);
}
