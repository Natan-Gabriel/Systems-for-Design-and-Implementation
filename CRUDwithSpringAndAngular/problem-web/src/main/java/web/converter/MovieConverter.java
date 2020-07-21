package web.converter;

import core.domain.Movie;
import org.springframework.stereotype.Component;
import web.dto.MovieDto;
@Component
public class MovieConverter extends BaseConverter<Movie, MovieDto> {
    @Override
    public Movie convertDtoToModel(MovieDto dto) {
        Movie movie = Movie.builder()
                .serialNumber(dto.getSerialNumber())
                .name(dto.getName())
                .year(dto.getYear())
                .rating(dto.getRating())
                .build();
        movie.setId(dto.getId());
        return movie;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto dto = MovieDto.builder()
                .serialNumber(movie.getSerialNumber())
                .name(movie.getName())
                .year(movie.getYear())
                .rating(movie.getRating())
                .build();
        dto.setId(movie.getId());
        return dto;
    }
}
