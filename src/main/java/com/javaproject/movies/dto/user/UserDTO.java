package com.javaproject.movies.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.javaproject.movies.entity.user.User;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    private LocalDate birthday;

    public static UserDTO fromUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setSecondName(user.getSecondName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setBirthday(user.getBirthday());
        return userDTO;
    }
}
