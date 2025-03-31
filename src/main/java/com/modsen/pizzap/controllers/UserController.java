package com.modsen.pizzap.controllers;

import com.modsen.pizzap.dto.UserDTO;
import com.modsen.pizzap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addUser(@RequestBody UserDTO user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody UserDTO user) {
        return userService.updateUser(userId, user);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDTO> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return userService.getUsers(pageable);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
