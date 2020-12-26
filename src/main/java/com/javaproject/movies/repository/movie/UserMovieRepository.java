package com.javaproject.movies.repository.movie;

import com.javaproject.movies.entity.movie.UserMovie;
import com.javaproject.movies.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMovieRepository extends JpaRepository<UserMovie, Long> {

    List<UserMovie> getAllByUser(User user);

    UserMovie getByIdAndUser(Long userMovieId, User user);
}
