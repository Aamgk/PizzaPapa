package com.modsen.pizzap.repositories;

import com.modsen.pizzap.models.Role;
import com.modsen.pizzap.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName name);
}
