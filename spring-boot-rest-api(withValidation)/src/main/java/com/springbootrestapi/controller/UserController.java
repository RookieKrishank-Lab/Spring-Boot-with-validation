package com.springbootrestapi.controller;

import com.springbootrestapi.dto.UserDTO;
import com.springbootrestapi.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor                                             //we are using constructor base DI and only one constructor will be there so no need of @Autowire
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user){
        UserDTO savedUser = userService.createUser(user);
//        return ResponseEntity.created(URI.create("/addUser")).body(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        List<UserDTO> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

    @PutMapping("/updateUserById/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long userId, @Valid @RequestBody UserDTO userDTO){
        userDTO.setUserId(userId);
        UserDTO updateUser = userService.updateUser(userDTO);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/deleteUserById/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User ID "+userId+" deleted successfully");
    }

    /*
    // specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFound,
                                                                        WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                resourceNotFound.getMessage(),
                webRequest.getDescription(false),
                "USER_NOT_FOUND"
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }*/
}
