package com.javaproject.movies.service.movie;

import com.javaproject.movies.entity.movie.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAll();

    Movie getById(Long id);
}
