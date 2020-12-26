package com.javaproject.movies.entity.movie;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.javaproject.movies.entity.BaseEntity;
import com.javaproject.movies.entity.user.User;
import lombok.*;

import javax.persistence.*;


/**
 * Фильмы, добавляемые пользователем в свой список
 * и помечаемые некоторыми параметрами.
 */
@Entity
@Table(name = "user_movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMovie extends BaseEntity {

    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Enumerated(EnumType.STRING)
    @Column(name = "movie_status")
    private MovieStatus movieStatus;

    // Оценка от 0 до 10
    @Column(name = "rating")
    private int rating;
}
