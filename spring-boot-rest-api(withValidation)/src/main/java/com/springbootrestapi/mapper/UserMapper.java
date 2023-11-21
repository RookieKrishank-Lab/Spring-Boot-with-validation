package com.springbootrestapi.mapper;

import com.springbootrestapi.dto.UserDTO;
import com.springbootrestapi.entity.User;
import org.springframework.context.annotation.Bean;


public class UserMapper {

    // Convert user JPA into UserDTO
    public UserDTO getUserDTO(User user){
        UserDTO userDTO = new UserDTO(
          user.getUserId(),
          user.getFirstName(),
          user.getLastName(),
          user.getEmail()
        );
        return userDTO;
    }

    // Convert UserDTO into user JPA
    public User getUser(UserDTO userDTO){
        User user = new User(
                userDTO.getUserId(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail()
        );
        return user;
    }
}
