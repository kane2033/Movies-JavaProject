package com.javaproject.movies.service.movie;

import com.javaproject.movies.entity.movie.UserMovie;
import com.javaproject.movies.entity.user.User;

import java.util.List;

public interface UserMovieService {

    List<UserMovie> getAllByUser(User user);

    UserMovie getByIdAndUser(Long userMovieId, User user);

    UserMovie add(UserMovie movie);

    List<UserMovie> update(List<UserMovie> movies);

    void deleteMultiple(List<UserMovie> movies);

    void deleteById(Long Id);
}
