package service;

import domain.Entities.Movie;
import domain.Validators.ValidatorException;
import repository.repos.DBMovieRepository;
import repository.repos.Repository;
import repository.repos.SortingRepository;
import repository.impl.Sort;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService implements IMovieService{
    private Repository<Long, Movie> movieRepository;

    public MovieService(Repository<Long, Movie> new_movieRepository){
        this.movieRepository=new_movieRepository;
    }

    public void addMovie(Movie movie) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //adds a movie
        movieRepository.save(movie);
    }

    public Set<Movie> getAllMovies() throws IOException, ClassNotFoundException, SQLException {
        //returns a set of all movies
        Iterable<Movie> movies = movieRepository.findAll();

        return StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toSet());
    }

    public Movie filterMoviesBySerial(String s) throws IOException, ClassNotFoundException, SQLException {
        //pre:receies a string
        //post:removes all movies that doens't have the given serial number
        Iterable<Movie> movies = movieRepository.findAll();
        Set<Movie> filteredMovies= new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie -> !movie.getSerial().equals(s));

        return filteredMovies.iterator().next();
    }

    public Optional<Movie> findOne(long id) throws IOException, ClassNotFoundException, SQLException {
        return this.movieRepository.findOne(id);
    }

    public Set<Movie> filterMoviesByName(String s) throws IOException, ClassNotFoundException, SQLException {
        Iterable<Movie> movies = movieRepository.findAll();
        Set<Movie> filteredMovies = new HashSet<>();
        movies.forEach(filteredMovies::add);
        filteredMovies.removeIf(movie -> !movie.getName().contains(s));
        return filteredMovies;
    }

    public void deleteMovie(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //deletes a movie
        movieRepository.delete(id);
    }
    public void updateMovie(Movie movie) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //updates a movie
        movieRepository.update(movie);
    }

    public List<Movie> getAllMoviesSorted() throws Exception {
        //returns a set of all movies
        Sort sort = new Sort(false, "name").and(new Sort("year"));

        if (movieRepository instanceof SortingRepository) {
            SortingRepository<Long, Movie> movieRepository1 = (DBMovieRepository) movieRepository;
            Iterable<Movie> movies = movieRepository1.findAll(sort);
            return StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toList());
        } else {
            throw new Exception("Invalid repo!");
        }
    }

}