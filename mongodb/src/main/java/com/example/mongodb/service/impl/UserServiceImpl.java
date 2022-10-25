package com.example.mongodb.service.impl;

import com.example.mongodb.entity.Role;
import com.example.mongodb.entity.User;
import com.example.mongodb.entity.dto.UserDto;
import com.example.mongodb.exception.NotUniequeUserEmailException;
import com.example.mongodb.exception.NotUniequeUserException;
import com.example.mongodb.repository.UserRepository;
import com.example.mongodb.service.UserService;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;
    private  final ModelMapper modelMapper;
    //if you are lazy to manual add admin to db.Uncomment and add user then can delete or comment again.

    /*@PostConstruct
    private  void init()
    {
        String pass=bCryptPasswordEncoder.encode("admin");
        User admin =new User("admin",pass,"admin@gmail.com",Role.admin);
        admin.setRole(Role.admin);
        userRepository.save(admin);
    }*/
    public User getByName(String name) {
        return userRepository.findByUserName(name);
    }
    @Override
    public String register(UserDto userDto) {
        try {
            checkUser(userDto.getUserName());
            checkUserEmail(userDto.getEmail());
            userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            userDto.setRole(Role.user);
            userRepository.save(modelMapper.map(userDto,User.class));
            return  "successful registration";
        }catch (NotUniequeUserException e )
        {
            return "user with such username already exist";
        }
        catch (NotUniequeUserEmailException e )
        {
            return  "user with such email already exists";
        }
    }
    private  void checkUser(String  name) throws NotUniequeUserException {
     User user;
     user=userRepository.findByUserName(name);
     if(user!=null)throw  new NotUniequeUserException("");
    }
    private  void checkUserEmail(String  email) throws NotUniequeUserException, NotUniequeUserEmailException {
        User user;
        user=userRepository.findByEmail(email);
        if(user!=null)throw  new NotUniequeUserEmailException("");
    }
}
