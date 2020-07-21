package service;

import domain.Entities.Movie;
import domain.Validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MovieService implements IMovieService {
    @Autowired
    IMovieService movieService;

    @Override
    public void addMovie(Movie movie) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        movieService.addMovie(movie);
    }

    @Override
    public Set<Movie> getAllMovies() throws IOException, ClassNotFoundException, SQLException {
        return movieService.getAllMovies();
    }

    @Override
    public Movie filterMoviesBySerial(String s) throws IOException, ClassNotFoundException, SQLException {
        return movieService.filterMoviesBySerial(s);
    }

    @Override
    public Optional<Movie> findOne(long id) throws IOException, ClassNotFoundException, SQLException {
        return movieService.findOne(id);
    }

    @Override
    public Set<Movie> filterMoviesByName(String s) throws IOException, ClassNotFoundException, SQLException {
        return movieService.filterMoviesByName(s);
    }

    @Override
    public void deleteMovie(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        movieService.deleteMovie(id);
    }

    @Override
    public void updateMovie(Movie movie) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        movieService.updateMovie(movie);
    }

    @Override
    public List<Movie> getAllMoviesSorted() throws Exception {
        return movieService.getAllMoviesSorted();
    }
}
