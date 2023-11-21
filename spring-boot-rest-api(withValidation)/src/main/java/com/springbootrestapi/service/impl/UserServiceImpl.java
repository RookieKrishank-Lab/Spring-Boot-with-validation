package com.springbootrestapi.service.impl;

import com.springbootrestapi.dto.UserDTO;
import com.springbootrestapi.entity.User;
import com.springbootrestapi.exception.EmailAlreadyExistException;
import com.springbootrestapi.exception.ResourceNotFoundException;
import com.springbootrestapi.mapper.UserMapper;
import com.springbootrestapi.repository.UserRepository;
import com.springbootrestapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        Optional<User> userEmail = userRepository.findByEmail(userDTO.getEmail());

        if(userEmail.isPresent()){
            throw new EmailAlreadyExistException("Email already exist for an user");
        }

        //Convert DTO to Entity
        User user = userMapper.getUser(userDTO);

        User savedUser = userRepository.save(user);
        log.info("{}",savedUser.getUserId());

        //Convert Entity to DTO
        UserDTO savedUserDTO = userMapper.getUserDTO(savedUser);
        log.info("{}",savedUserDTO.getUserId());
        return savedUserDTO;

    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User","userId",userId)
        );
        return userMapper.getUserDTO(user);

    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::getUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User user = userMapper.getUser(userDTO);
        User searchedUser = userRepository.findById(user.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User","userId", user.getUserId())
        );
//       Optional<User> searchedUser = userRepository.findById(user.getUserId());           //If we use like this in return type we get an error as our method return type is not Optional
        searchedUser.setFirstName(user.getFirstName());
        searchedUser.setLastName(user.getLastName());
        searchedUser.setEmail(user.getEmail());
        User updatedUser =  userRepository.save(searchedUser);
        return userMapper.getUserDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User searchedUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User","userId", userId)
        );
        userRepository.deleteById(userId);
    }
}
