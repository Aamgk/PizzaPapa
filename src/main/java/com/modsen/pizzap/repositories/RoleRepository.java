package com.modsen.pizzap.repositories;

import com.modsen.pizzap.models.Role;
import com.modsen.pizzap.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(RoleName name);
}
