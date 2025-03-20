package com.modsen.pizzap.services.Impl;

import com.modsen.pizzap.dto.UserDTO;
import com.modsen.pizzap.mappers.UserMapper;
import com.modsen.pizzap.models.User;
import com.modsen.pizzap.repositories.RoleRepository;
import com.modsen.pizzap.repositories.UserRepository;
import com.modsen.pizzap.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public void createUser(UserDTO user) {
        User newUser = userMapper.userDTOToUser(user);
        newUser.setRole(roleRepository.findById(user.roleId()).orElseThrow(() -> new RuntimeException("Role not found")));
        userRepository.save(newUser);
    }

    @Override
    public void updateUser(Long userId, UserDTO user) {
        User updatedUser = userMapper.userDTOToUser(user);
        updatedUser.setRole(roleRepository.findById(user.roleId()).orElseThrow(() -> new RuntimeException("Role not found")));
        User oldUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        oldUser.setUserName(updatedUser.getUserName());
        oldUser.setRole(updatedUser.getRole());
        oldUser.setEmail(updatedUser.getEmail());
        oldUser.setPassword(updatedUser.getPassword());
        userRepository.save(oldUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserDTO getUser(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserDTO> getUsers() {
        return new ArrayList<>(userRepository.findAll())
                .stream()
                .map(userMapper).toList();
    }
}
