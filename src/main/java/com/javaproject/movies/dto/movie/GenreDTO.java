package com.javaproject.movies.dto.movie;

import com.javaproject.movies.entity.movie.Genre;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GenreDTO {

    private Long id;
    private String name;

    public Genre fromDTOtoGenre() {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(name);
        return genre;
    }

    public static List<Genre> fromDTOListToGenreList(List<GenreDTO> dtos) {
        List<Genre> genresEntities = new ArrayList<>();
        for (GenreDTO dto : dtos) {
            genresEntities.add(dto.fromDTOtoGenre());
        }
        return genresEntities;
    }

    public static GenreDTO fromGenreToDTO(Genre genre) {
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }

    public static List<GenreDTO> fromGenreListToDTOList(List<Genre> genres) {
        List<GenreDTO> dtos = new ArrayList<>();
        for (Genre genre : genres) {
            dtos.add(fromGenreToDTO(genre));
        }
        return dtos;
    }

}
