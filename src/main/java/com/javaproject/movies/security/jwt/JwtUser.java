package com.javaproject.movies.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tomcat.jni.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

//пользователь, с которым работает spring security
public class JwtUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String firstName; //имя
    private final String lastName; //фамилия
    private final String secondName; //отчество
    private final String email;
    private final String password;
    private final LocalDate birthday;
    private final boolean enabled;
    private final Date lastPasswordResetDate;
    private final Collection<? extends GrantedAuthority> authorities; //права

    public JwtUser(Long id,
                   String username,
                   String firstName,
                   String lastName,
                   String secondName,
                   String email,
                   String password,
                   LocalDate birthday,
                   Collection<? extends GrantedAuthority> authorities,
                   boolean enabled,
                   Date lastPasswordResetDate
    ) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public LocalDate getBirthday() {return birthday;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
}
