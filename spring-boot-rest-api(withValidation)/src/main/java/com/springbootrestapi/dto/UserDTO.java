package com.springbootrestapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;
    // User firstName cant be null or empty
    @NotEmpty(message = "User firstName cant be null or empty")
    private String firstName;
    // User lastName cant be null or empty
    @NotEmpty(message = "User lastName cant be null or empty")
    private String lastName;
    @NotEmpty(message = "User mail cant be null or empty")
    @Email
    private String email;
}
