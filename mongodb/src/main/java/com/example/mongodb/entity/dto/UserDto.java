package com.example.mongodb.entity.dto;

import com.example.mongodb.entity.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private  String id;
    @NotBlank
    private  String userName;
    @NotBlank
    private  String password;
    @NotBlank
    @Email
    private  String email;
    private Role role;
}
