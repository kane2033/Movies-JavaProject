package com.javaproject.movies.controller.movie;


import com.javaproject.movies.dto.movie.MovieDTO;
import com.javaproject.movies.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/movies/")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(path = "")
    public ResponseEntity<MovieDTO> getMovieById(@RequestParam Long id) {
        MovieDTO dto = MovieDTO.fromMovieToDTO(movieService.getById(id));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> dtos = MovieDTO.fromMoviesListToMovieDTOList(movieService.getAll());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
