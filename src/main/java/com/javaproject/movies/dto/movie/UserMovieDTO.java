package com.javaproject.movies.dto.movie;

import com.javaproject.movies.entity.movie.MovieStatus;
import com.javaproject.movies.entity.movie.UserMovie;
import com.javaproject.movies.entity.user.Status;
import com.javaproject.movies.entity.user.User;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserMovieDTO {

    private Long id;
    private String username;
    private MovieDTO movie;
    private String movieStatus;
    private int rating;
    private Date created;

    public UserMovie fromDTOtoUserMovie(User user) {
        UserMovie userMovie = new UserMovie();
        userMovie.setId(id);
        userMovie.setUser(user);
        userMovie.setMovie(movie.fromDTOtoMovie());
        userMovie.setMovieStatus(movieStatus == null ? null : MovieStatus.valueOf(movieStatus));
        userMovie.setRating(rating);
        userMovie.setStatus(Status.ACTIVE);
        userMovie.setCreated(created == null ? Date.from(Instant.now()) : created);
        userMovie.setUpdated(Date.from(Instant.now()));
        return userMovie;
    }

    public static List<UserMovie> fromDTOListToUserMovieList(List<UserMovieDTO> dtos, User user) {
        List<UserMovie> userMovies = new ArrayList<>();
        for (UserMovieDTO dto : dtos) {
            userMovies.add(dto.fromDTOtoUserMovie(user));
        }
        return userMovies;
    }

    public static UserMovieDTO fromUserMovieToDTO(UserMovie userMovie) {
        UserMovieDTO dto = new UserMovieDTO();
        dto.setId(userMovie.getId());
        dto.setUsername(userMovie.getUser().getUsername());
        dto.setMovie(MovieDTO.fromMovieToDTO(userMovie.getMovie()));
        dto.setMovieStatus(userMovie.getMovieStatus() == null ? null : userMovie.getMovieStatus().toString());
        dto.setRating(userMovie.getRating());
        dto.setCreated(userMovie.getCreated());
        return dto;
    }

    // переводим список сущностей в список дто
    public static List<UserMovieDTO> fromUserMovieListToDTOList(List<UserMovie> userMovies) {
        List<UserMovieDTO> dtos = new ArrayList<>();
        for (UserMovie movie : userMovies) {
            dtos.add(UserMovieDTO.fromUserMovieToDTO(movie));
        }
        return dtos;
    }
}
