package com.example.mongodb.web;

import com.example.mongodb.entity.dto.UserDto;
import com.example.mongodb.service.impl.UserServiceImpl;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @RequestMapping("/register")
    public String registerUser(@Valid @RequestBody UserDto userDto) {
        return userService.register(userDto);
    }
}
