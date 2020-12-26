package com.javaproject.movies.repository.movie;

import com.javaproject.movies.entity.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
