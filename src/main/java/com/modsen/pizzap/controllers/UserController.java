package com.modsen.pizzap.controllers;

import com.modsen.pizzap.dto.UserDTO;
import com.modsen.pizzap.models.User;
import com.modsen.pizzap.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Data
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public void addUser(@RequestBody UserDTO user) {
        userService.createUser(user);
    }

    @PostMapping("/updateUser/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody UserDTO user) {
        userService.updateUser(userId, user);
    }

    @GetMapping("/getUser/{userId}")
    public UserDTO getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/getAllUsers")
    public List<UserDTO> getAllUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
