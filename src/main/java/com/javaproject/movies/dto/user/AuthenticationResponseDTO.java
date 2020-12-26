package com.javaproject.movies.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationResponseDTO {
    private String username;
    private String token;
}
