package com.modsen.pizzap.mappers;

import com.modsen.pizzap.models.Role;
import org.springframework.stereotype.Service;
import com.modsen.pizzap.dto.UserDTO;
import com.modsen.pizzap.models.User;

import java.util.function.Function;

@Service
public class UserMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getRole().getId()
        );
    }

    public User userDTOToUser(UserDTO userDTO) {
        Role role = new Role();
        return new User(
                userDTO.userName(),
                userDTO.password(),
                userDTO.email(),
                role
        );
    }
}
