package com.javaproject.movies.dto.user;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String username;
    private String password;
}
