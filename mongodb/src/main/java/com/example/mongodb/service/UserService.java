package com.example.mongodb.service;

import com.example.mongodb.entity.dto.UserDto;

public interface UserService {
    String register(UserDto userDto);
}
