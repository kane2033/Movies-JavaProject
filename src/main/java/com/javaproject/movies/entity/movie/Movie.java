package com.javaproject.movies.entity.movie;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.javaproject.movies.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    //соединение М:М через промежуточную таблицу
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_genres",
            joinColumns = {@JoinColumn(name = "movie_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id", referencedColumnName = "id")})
    private List<Genre> genres;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "synopsis")
    private String synopsis;

    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<UserMovie> userMovies;

}
