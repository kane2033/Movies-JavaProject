package com.javaproject.movies.entity.movie;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.javaproject.movies.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genres")
@Data
public class Genre extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER)
    private List<Movie> movies;
}
