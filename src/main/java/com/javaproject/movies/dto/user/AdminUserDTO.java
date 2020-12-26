package com.javaproject.movies.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.javaproject.movies.entity.user.Status;
import com.javaproject.movies.entity.user.User;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private LocalDate birthday;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status));
        user.setBirthday(birthday);
        return user;
    }

    public static AdminUserDTO fromUser(User user) {
        AdminUserDTO adminUserDTO = new AdminUserDTO();
        adminUserDTO.setId(user.getId());
        adminUserDTO.setUsername(user.getUsername());
        adminUserDTO.setFirstName(user.getFirstName());
        adminUserDTO.setLastName(user.getLastName());
        adminUserDTO.setEmail(user.getEmail());
        adminUserDTO.setStatus(user.getStatus().name());
        adminUserDTO.setBirthday(user.getBirthday());
        return adminUserDTO;
    }
}
