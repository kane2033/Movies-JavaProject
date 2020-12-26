package com.javaproject.movies.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.javaproject.movies.entity.BaseEntity;
import com.javaproject.movies.entity.movie.UserMovie;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "first_name")
    private String firstName; //имя

    @Column(name = "last_name")
    private String lastName; //фамилия

    @Column(name = "second_name")
    private String secondName; //отчество

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "birthday")
    private LocalDate birthday;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    //соединение М:М через промежуточную таблицу user_roles
    private List<Role> roles;

    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<UserMovie> userMovies;

    public User(String username, String firstName, String lastName, String secondName, String email, String password, LocalDate birthday) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }
}
