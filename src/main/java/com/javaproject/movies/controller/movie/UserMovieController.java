package com.javaproject.movies.controller.movie;


import com.javaproject.movies.dto.movie.UserMovieDTO;
import com.javaproject.movies.entity.movie.UserMovie;
import com.javaproject.movies.entity.user.User;
import com.javaproject.movies.service.movie.UserMovieService;
import com.javaproject.movies.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/movies/user-movies/")
public class UserMovieController {
    private final UserMovieService movieService;
    private final UserService userService;

    @Autowired
    public UserMovieController(UserMovieService movieService, UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<UserMovieDTO>> getAllByUsername(@RequestParam String username) {
        User user = userService.findByUsername(username);
        List<UserMovieDTO> dtos = UserMovieDTO.fromUserMovieListToDTOList(movieService.getAllByUser(user));
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(path = "")
    public ResponseEntity<UserMovie> getByIdAndUsername(@RequestParam Long id, @RequestParam String username) {
        User user = userService.findByUsername(username);
        return new ResponseEntity<>(movieService.getByIdAndUser(id, user), HttpStatus.OK);
    }

    @PostMapping(path = "add", consumes = {"application/json"})
    public ResponseEntity<String> addUserMovie(@RequestBody UserMovieDTO requestDTO) {
        String username = requestDTO.getUsername();
        User user = userService.findByUsername(username);

        UserMovie userMovie = requestDTO.fromDTOtoUserMovie(user);
        movieService.add(userMovie);

        return ResponseEntity.ok("Фильм успешно добавлен в ваш список, " + username);
    }

    @PostMapping(path = "update", consumes = {"application/json"})
    public ResponseEntity<String> updateUserMovies(@RequestBody List<UserMovieDTO> dtos) {
        String username = dtos.get(0).getUsername();
        User user = userService.findByUsername(username);

        List<UserMovie> userMovies = UserMovieDTO.fromDTOListToUserMovieList(dtos, user);
        movieService.update(userMovies);

        return ResponseEntity.ok("Список ваших фильмов успешно обновлен, " + username);
    }

    @PostMapping(path = "delete/multiple", consumes = {"application/json"})
    public ResponseEntity<String> deleteUserMovieById(@RequestBody List<UserMovieDTO> dtos) {
        String username = dtos.get(0).getUsername();
        User user = userService.findByUsername(username);

        List<UserMovie> userMovies = UserMovieDTO.fromDTOListToUserMovieList(dtos, user);
        movieService.deleteMultiple(userMovies);

        return ResponseEntity.ok("Выбранные фильмы успешно удалены");
    }

    @PostMapping(path = "delete", consumes = {"application/json"})
    public ResponseEntity<String> deleteUserMovieById(@RequestParam Long id) {
        movieService.deleteById(id);
        return ResponseEntity.ok("Фильм успешно удален");
    }
}
