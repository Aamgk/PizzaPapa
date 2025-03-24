package com.modsen.pizzap.mappers;

import com.modsen.pizzap.dto.RoleDTO;
import com.modsen.pizzap.models.Role;

import java.util.function.Function;

public class RoleMapper implements Function<Role, RoleDTO> {

    @Override
    public RoleDTO apply(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getRoleName().toString()
        );
    }
}
