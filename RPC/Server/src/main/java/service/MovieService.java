package service;

import domain.Movie;
import domain.Validators.ValidatorException;
import repository.DBMovieRepository;
import repository.Repository;
import repository.SortingRepository;
import repository.impl.Sort;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService {
    private Repository<Long, Movie> movieRepository;
    private ExecutorService executorService;

    public MovieService(Repository<Long, Movie> new_movieRepository, ExecutorService executorService){
        this.movieRepository=new_movieRepository;
        this.executorService = executorService;
    }

    public void addMovie(Movie movie) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //adds a movie
        movieRepository.save(movie);
    }

    public Future<Set<Movie>> getAllMovies() throws IOException, ClassNotFoundException, SQLException {
        //returns a set of all movies
        Iterable<Movie> movies = movieRepository.findAll();

        return executorService.submit(() -> StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toSet()));
    }

    public Future<Movie> filterMoviesBySerial(String s) throws IOException, ClassNotFoundException, SQLException {
        //pre:receies a string
        //post:removes all movies that doens't have the given serial number
        Iterable<Movie> movies = movieRepository.findAll();
        Set<Movie> filteredMovies= new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie -> !movie.getSerial().equals(s));

        return executorService.submit(() -> filteredMovies.iterator().next());
    }

    public Future<Optional<Movie>> findOne(long id) throws IOException, ClassNotFoundException, SQLException {
        return executorService.submit(() ->this.movieRepository.findOne(id));
    }

    public Future<Set<Movie>> filterMoviesByName(String s) throws IOException, ClassNotFoundException, SQLException {
        Iterable<Movie> movies = movieRepository.findAll();
        Set<Movie> filteredMovies = new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie -> !movie.getName().contains(s));
        return executorService.submit(() ->filteredMovies);
    }

    public void deleteMovie(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //deletes a movie
        movieRepository.delete(id);
    }
    public void updateMovie(Movie movie) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //updates a movie
        movieRepository.update(movie);
    }

    public Future<List<Movie>> getAllMoviesSorted() throws Exception {
        //returns a set of all movies
        Sort sort = new Sort(false, "name").and(new Sort("year"));

        if (movieRepository instanceof SortingRepository) {
            SortingRepository<Long, Movie> movieRepository1 = (DBMovieRepository) movieRepository;
            Iterable<Movie> movies = movieRepository1.findAll(sort);
            return executorService.submit(() -> StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toList()));
        } else {
            throw new Exception("Invalid repo!");
        }
    }

}