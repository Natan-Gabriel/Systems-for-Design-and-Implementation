package core.service;

import core.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.domain.Movie;
import core.domain.Validators.MovieValidator;
import core.domain.Validators.Validator;
import core.domain.Validators.ValidatorException;
import core.repository.JPAMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class MovieService {
    public static final Logger log = LoggerFactory.getLogger(MovieService.class);
    @Autowired
    private JPAMovieRepository movieRepository;
    private Validator<Movie> movieValidator=new MovieValidator();

    public Movie addMovie(Movie movie) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //adds a movie
        movieValidator.validate(movie);
        log.trace("addMovie - method entered: movie={}", movie);
        movieRepository.save(movie);
        log.trace("addMovie - method finished");
        return movie;
    }

    public boolean existsByID(long id){
        return movieRepository.existsById(id);
    }


    public Set<Movie> getAllMovies() throws IOException, ClassNotFoundException, SQLException {
        //returns a set of all movies
        log.trace("getAllMovies - method entered");
        Iterable<Movie> movies = movieRepository.findAll();
        log.trace("getAllMovies - method finished");
        return StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toSet());
    }

    public Movie filterMoviesBySerial(String s) throws IOException, ClassNotFoundException, SQLException {
        //pre:receies a string
        //post:removes all movies that doens't have the given serial number
        log.trace("filterMoviesBySerial - method entered: filter={}", s);
        Iterable<Movie> movies = movieRepository.findAll();
        Set<Movie> filteredMovies= new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie -> !movie.getSerialNumber().equals(s));
        log.trace("filterMoviesBySerial - method finished");
        return filteredMovies.iterator().next();
    }

    public Optional<Movie> findOne(long id) throws IOException, ClassNotFoundException, SQLException {
        log.trace("filterOne - method entered: id={}", id);
        //Movie movie=this.movieRepository.getOne(id);
        Optional<Movie> movie=this.movieRepository.findById(id);
        log.trace("filterOne - method finished");
        return movie;
    }

    public List<Movie> filterMoviesByName(String s,long size,long page)  {
        log.trace("filterMoviesByName - method entered: filter={}", s);
        /*Iterable<Movie> movies = movieRepository.findAll();
        Set<Movie> filteredMovies = new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie -> !movie.getName().contains(s));*/
        Pageable firstPageWithElements = PageRequest.of((int)page, (int)size);

        List<Movie> allMovies =
                movieRepository.findAllByName(s, firstPageWithElements);

        log.trace("filterMoviesByName - method finished");
        return allMovies;
    }

    public void deleteMovie(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //deletes a movie
        log.trace("deleteMovie - method entered: id={}", id);
        movieRepository.deleteById(id);
        log.trace("deleteMovie - method finished", id);
    }
    @Transactional
    public Movie updateMovie(long id,Movie movie) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //updates a movie
        log.trace("updateMovie - method entered: movie={}", movie);
        if (!existsByID(id)) {
            log.trace("updateMovie - ERROR - movieId is invalid");
            throw new ValidatorException("Invalid movie ID!");
        }
        movieValidator.validate(movie);
        movieRepository.findById(id)
                .ifPresent(s -> {
                    s.setName(movie.getName());
                    s.setYear(movie.getYear());
                    s.setSerialNumber(movie.getSerialNumber());
                    s.setRating(movie.getRating());
                    log.debug("updateMovie - updated: s={}", s);
                });
        log.trace("updateMovie - method finished");
        return movie;
    }

    public List<Movie> getAllMoviesSorted(long size,long page,String field,String direction) {
        //returns a set of all movies
        log.trace("getAllMoviesSorted - method entered");
        //List<Movie> sorted = movieRepository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.ASC, "name"));
        System.out.println(direction);
        Sort.Direction dir;
        if (direction.equals("DESC"))
        {
            dir=Sort.Direction.DESC;
            System.out.println("desc");
        }
        else {
            dir = Sort.Direction.ASC;
            System.out.println("asc");
        }

        Pageable firstPageWithTwoElements = PageRequest.of((int)page, (int)size,Sort.by(dir,field));

        Page<Movie> sorted = movieRepository.findAll(firstPageWithTwoElements);
        log.trace("getAllMoviesSorted - method finished");
        return sorted.getContent();

    }

}