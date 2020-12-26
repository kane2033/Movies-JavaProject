package com.javaproject.movies.dto.user;

import com.javaproject.movies.entity.user.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequestDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String secondName;
    private String email;
    private String password;
    private String birthday;

    public void updateUser(User user) {
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setBirthday(LocalDate.parse(birthday));
    }
}


