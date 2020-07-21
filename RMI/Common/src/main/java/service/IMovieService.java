package service;

import domain.Entities.Movie;
import domain.Validators.ValidatorException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IMovieService {

    public void addMovie(Movie movie) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;
    public Set<Movie> getAllMovies() throws IOException, ClassNotFoundException, SQLException;
    public Movie filterMoviesBySerial(String s) throws IOException, ClassNotFoundException, SQLException;
    public Optional<Movie> findOne(long id) throws IOException, ClassNotFoundException, SQLException;
    public Set<Movie> filterMoviesByName(String s) throws IOException, ClassNotFoundException, SQLException;
    public void deleteMovie(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;
    public void updateMovie(Movie movie) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;
    public List<Movie> getAllMoviesSorted() throws Exception;
}
