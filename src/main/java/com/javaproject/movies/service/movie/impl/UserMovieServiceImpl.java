package com.javaproject.movies.service.movie.impl;

import com.javaproject.movies.entity.movie.UserMovie;
import com.javaproject.movies.entity.user.User;
import com.javaproject.movies.repository.movie.UserMovieRepository;
import com.javaproject.movies.service.movie.UserMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserMovieServiceImpl implements UserMovieService {

    private final UserMovieRepository movieRepository;

    @Autowired
    public UserMovieServiceImpl(UserMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<UserMovie> getAllByUser(User user) {
        return movieRepository.getAllByUser(user);
    }

    @Override
    public UserMovie getByIdAndUser(Long userMovieId, User user) {
        return user.getUserMovies().stream()
                .filter(movie -> userMovieId.equals(movie.getId()))
                .findAny()
                .orElse(null);
    }

    @Override
    public UserMovie add(UserMovie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<UserMovie> update(List<UserMovie> movies) {
        return movieRepository.saveAll(movies);
    }

    @Override
    public void deleteMultiple(List<UserMovie> movies) {
        movieRepository.deleteInBatch(movies);
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }
}
