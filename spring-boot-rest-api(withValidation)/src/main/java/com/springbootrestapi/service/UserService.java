package com.springbootrestapi.service;

import com.springbootrestapi.dto.UserDTO;
import com.springbootrestapi.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long userId);
    List<UserDTO> getAllUser();
    UserDTO updateUser(UserDTO userDTO);
    void deleteUser(Long userId);
}
