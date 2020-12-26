package com.javaproject.movies.dto.movie;

import com.javaproject.movies.entity.movie.Movie;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class MovieDTO {

    private Long id;
    private String name;
    private List<GenreDTO> genres;
    private Date releaseDate;
    private String synopsis;

    public Movie fromDTOtoMovie() {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setName(name);
        movie.setGenres(GenreDTO.fromDTOListToGenreList(genres));
        movie.setReleaseDate(releaseDate);
        movie.setSynopsis(synopsis);
        return movie;
    }

    public static MovieDTO fromMovieToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setName(movie.getName());
        dto.setGenres(GenreDTO.fromGenreListToDTOList(movie.getGenres()));
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setSynopsis(movie.getSynopsis());
        return dto;
    }

    public static List<MovieDTO> fromMoviesListToMovieDTOList(List<Movie> movies) {
        List<MovieDTO> dtos = new ArrayList<>();
        for (Movie movie : movies) {
            dtos.add(fromMovieToDTO(movie));
        }
        return dtos;
    }
}
