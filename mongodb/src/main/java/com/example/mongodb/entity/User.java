package com.example.mongodb.entity;

import java.util.Collection;
import java.util.Collections;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document
@Data
@NoArgsConstructor
public class User {
    @Id
    private  String id;
    @NotBlank
    @Indexed(unique = true)
    private  String userName;
    @NotBlank
    private  String password;
    @NotBlank
    @Email
    private String email;
    private  Role role;
    public User(String userName, String password, String email,Role role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);}
}
