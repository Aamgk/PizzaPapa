package com.modsen.pizzap.services;

import com.modsen.pizzap.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO updateUser(Long userId, UserDTO user);

    void deleteUser(Long userId);

    UserDTO getUser(Long userId);

    Page<UserDTO> getUsers(Pageable pageable);
}
