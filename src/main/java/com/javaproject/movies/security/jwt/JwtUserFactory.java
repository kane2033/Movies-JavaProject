package com.javaproject.movies.security.jwt;

import com.javaproject.movies.entity.user.Role;
import com.javaproject.movies.entity.user.Status;
import com.javaproject.movies.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {

    }

    //фарбика создания jwtuser
    // на основе user
    // для работы spring security
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getSecondName(),
                user.getEmail(),
                user.getPassword(),
                user.getBirthday(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated()
        );
    }

    //преобразование ролей пользователя в список GrantedAuthority (класс, с кот. работает spring security)
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
