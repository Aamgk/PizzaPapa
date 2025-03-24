package com.modsen.pizzap.dto;

import com.modsen.pizzap.models.Order;
import com.modsen.pizzap.models.Role;

import java.util.List;

public record UserDTO (
        Long id,
        String userName,
        String password,
        String email,
        Long roleId
){}
