package web.controller;

import core.domain.Client;
import core.domain.Movie;
import core.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.MovieConverter;
import web.dto.ClientDto;
import web.dto.ClientsDto;
import web.dto.MovieDto;
import web.dto.MoviesDto;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@RestController
public class MovieController {
    public static final Logger log= LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;


    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    Set<MovieDto> getMovies() throws SQLException, IOException, ClassNotFoundException {
        log.trace("getMovies - method entered");
        Set<MovieDto> movies= (Set<MovieDto>) movieConverter
                .convertModelsToDtos(movieService.getAllMovies());
        log.trace("getMovies - method finished");
        return movies;

    }
    @RequestMapping(value = "/movies/get/{id}", method = RequestMethod.GET)
    MovieDto getMovie(@PathVariable long id) throws SQLException, IOException, ClassNotFoundException {
        log.trace("getMovie - method entered:id={}",id);
        Movie movie = movieService.findOne(id).orElse(null);
        MovieDto result = movieConverter.convertModelToDto(movie);
        log.trace("getMovie - method entered:id={}",id);
        return result;

    }

    @RequestMapping(value = "/movies/distinct/name/{name}", method = RequestMethod.GET)
    Set<MovieDto> findDistinctByName(@PathVariable String name)  {
        log.trace("findDistinctByName - method entered:id={}",name);
        List<Movie> movies = movieService.findDistinctByName(name);
        log.trace("findDistinctByName - method finished:id={}",name);
        return (Set<MovieDto>) movieConverter.convertModelsToDtos(movies);

    }
    @RequestMapping(value = "/movies/distinct/serial/{serial}", method = RequestMethod.GET)
    Set<MovieDto> findDistinctBySerial(@PathVariable String serial)  {
        log.trace("findDistinctByName - method entered:id={}",serial);
        List<Movie> movies = movieService.findDistinctBySerial(serial);
        log.trace("findDistinctByName - method finished:id={}",serial);
        return (Set<MovieDto>) movieConverter.convertModelsToDtos(movies);

    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    MovieDto saveMovie(@RequestBody MovieDto movieDto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("saveMovie - method entered: movieDto={}", movieDto);
        MovieDto movie=movieConverter.convertModelToDto(movieService.addMovie(
                movieConverter.convertDtoToModel(movieDto)
        ));
        log.trace("saveMovie - method finished: movieDto={}", movieDto);
        return movie;
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    MovieDto updateMovie(@RequestBody MovieDto movieDto) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("updateMovie - method entered: movieDto={}", movieDto);
        MovieDto movie=movieConverter.convertModelToDto( movieService.updateMovie(movieDto.getId(),
                movieConverter.convertDtoToModel(movieDto)));
        log.trace("updateMovie - method finished: movieDto={}", movieDto);
        return movie;
    }


    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteMovie(@PathVariable Long id) throws ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        log.trace("deleteMovie - method entered: id={}", id);
        movieService.deleteMovie(id);
        log.trace("deleteMovie - method finished: id={}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "/movies/filter/{filter}/{size}/{page}", method = RequestMethod.GET)
    Set<MovieDto> filterMoviesByName(@PathVariable String filter,@PathVariable long size,@PathVariable long page) {
        log.trace("filterMoviesByName - method entered filter{}",filter);
        Set<MovieDto> l= (Set<MovieDto>) movieConverter
                .convertModelsToDtos(movieService.filterMoviesByName(filter,size,page));
        log.trace("filterMoviesByName - method finished filter{}",filter);
        return l;
    }
    @RequestMapping(value = "/movies/sorted/{size}/{page}/{field}/{direction}", method = RequestMethod.GET)
    List<MovieDto> getMoviesSorted(@PathVariable long size, @PathVariable long page, @PathVariable String field, @PathVariable String direction) {
        log.trace("getMoviesSorted - method entered ");
        List<MovieDto> l= (List<MovieDto>) movieConverter
                .convertModelsToDtos(movieService.getAllMoviesSorted(size,page,field,direction));
        log.trace("getMoviesSorted - method finished");
        return l;
    }
}

