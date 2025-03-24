package com.modsen.pizzap.services.Impl;

import com.modsen.pizzap.dto.UserDTO;
import com.modsen.pizzap.exception.DuplicateResourceException;
import com.modsen.pizzap.exception.ResourceNotFoundException;
import com.modsen.pizzap.exception.error.ErrorMessages;
import com.modsen.pizzap.mappers.UserMapper;
import com.modsen.pizzap.models.Role;
import com.modsen.pizzap.models.User;
import com.modsen.pizzap.repositories.RoleRepository;
import com.modsen.pizzap.repositories.UserRepository;
import com.modsen.pizzap.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<UserDTO> createUser(UserDTO user) {
        checkUserExistence(user.email());

        User newUser = userMapper.userDTOToUser(user);
        newUser.setRole(findUserRoleByIdOrThrow(user.roleId()));
        userRepository.save(newUser);
        return new ResponseEntity<>(
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(Long userId, UserDTO user) {
        User updatedUser = userMapper.userDTOToUser(user);
        updatedUser.setRole(findUserRoleByIdOrThrow(user.roleId()));
        User oldUser = findUserByIdOrThrow(userId);
        oldUser.setUserName(updatedUser.getUserName());
        oldUser.setRole(updatedUser.getRole());
        oldUser.setEmail(updatedUser.getEmail());
        oldUser.setPassword(updatedUser.getPassword());
        userRepository.save(oldUser);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @Override
    public void deleteUser(Long userId) {
        User user = findUserByIdOrThrow(userId);
        userRepository.delete(user);
    }

    @Override
    public UserDTO getUser(Long userId) {
        User user = findUserByIdOrThrow(userId);
        return userMapper.apply(user);
    }

    @Override
    public Page<UserDTO> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper);
    }

    private User findUserByIdOrThrow(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_MESSAGE, "User", userId)));
    }

    private void checkUserExistence(String userEmail){
        if(userRepository.existsByEmail(userEmail)){
            throw new DuplicateResourceException(String.format(ErrorMessages.DUPLICATE_RESOURCE_MESSAGE, "User", "email"));
        }
    }

    private Role findUserRoleByIdOrThrow(Long roleId){
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_MESSAGE, "Role", roleId)));
    }
}
