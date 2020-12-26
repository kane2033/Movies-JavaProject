package com.javaproject.movies.service.movie.impl;

import com.javaproject.movies.entity.movie.Movie;
import com.javaproject.movies.repository.movie.MovieRepository;
import com.javaproject.movies.service.movie.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getById(Long id) {
        return movieRepository.getOne(id);
    }
}
