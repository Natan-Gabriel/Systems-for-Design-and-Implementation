package service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieServiceTest {
//    private MovieService service;
//    private Validator<Movie> movieValidator;
//    private InMemoryRepository repository;
//    private String serialNumber="1";
//    private String name="Cats";
//    private int year=2019;
//    private int rating=1;
//    private long id=12;
//
//    @Before
//    public void setUp() throws Exception {
//        movieValidator = new MovieValidator();
//        repository = new InMemoryRepository(movieValidator);
//        service = new MovieService(repository);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        movieValidator=null;
//        repository=null;
//        service=null;
//    }
//
//    @Test
//    public void testAddMovie() throws Exception{
//        Movie mov = new Movie(name, year, rating, serialNumber);
//        mov.setId(id);
//        service.addMovie(mov);
//        assertEquals("should be equal", mov, service.getAllMovies().toArray()[0]);
//    }
//
//    @Test
//    public void testGetAllMovies() throws Exception{
//        Movie mov = new Movie(name, year, rating, serialNumber);
//        mov.setId(id);
//        service.addMovie(mov);
//        Movie mov2= new Movie("Dogs", 1999, 99, "1234");
//        mov2.setId((long) 2000);
//        service.addMovie(mov2);
//        assertEquals("should be equal", mov, service.getAllMovies().toArray()[1]);
//        assertEquals("should be equal", mov2, service.getAllMovies().toArray()[0]);
//    }
//
//    @Test
//    public void testFilterMoviesBySerial() throws Exception{
//        Movie mov = new Movie(name, year, rating, serialNumber);
//        mov.setId(id);
//        service.addMovie(mov);
//        Movie mov2= new Movie("Dogs", 1999, 99, "1234");
//        mov2.setId((long) 2000);
//        service.addMovie(mov2);
//        assertEquals("should be equal", mov, service.filterMoviesBySerial(serialNumber));
//    }
}
