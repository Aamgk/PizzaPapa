package com.modsen.pizzap.services;

import com.modsen.pizzap.dto.UserDTO;

import java.util.List;

public interface UserService {
    void createUser(UserDTO user);

    void updateUser(Long userId, UserDTO user);

    void deleteUser(Long userId);

    UserDTO getUser(Long userId);

    List<UserDTO> getUsers();
}
