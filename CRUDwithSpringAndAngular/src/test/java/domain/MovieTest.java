package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MovieTest {
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private String serialNumber= "sn01";
    private String new_serialNumber= "sn02";
    private String name="movieName";
    private String new_name="movieName1";
    private int year=2020;
    private int new_year=2021;
    private int rating=10;
    private int new_rating=9;

    private Movie movie;

    @Before
    public void setUp() throws Exception {
        movie = new Movie(name,year,rating,serialNumber);
        movie.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        movie=null;
    }

    @Test
    public void testGetSerialNumber() throws Exception {
        assertEquals("Serial numbers should be equal",serialNumber, movie.getSerial());
    }

    @Test
    public void testSetSerialNumber() throws Exception {
        movie.setSerial(new_serialNumber);
        assertEquals("Serial numbers should be equal", new_serialNumber, movie.getSerial());
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, movie.getId());
    }

    @Test
    public void testSetId() throws Exception {
        movie.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, movie.getId());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Names should be equal", name, movie.getName());
    }

    @Test
    public void testSetName() throws Exception {
        movie.setName(new_name);
        assertEquals("Ids should be equal", new_name, movie.getName());
    }

    @Test
    public void testGetYear() throws Exception {
        assertEquals("Years should be equal", year, movie.getYear());
    }

    @Test
    public void testSetYear() throws Exception {
        movie.setYear(new_year);
        assertEquals("Years should be equal", new_year, movie.getYear());
    }

    @Test
    public void testGetRating() throws Exception {
        assertEquals("Ratings should be equal", rating, movie.getRating());
    }

    @Test
    public void testSetRating() throws Exception {
        movie.setRating(new_rating);
        assertEquals("Ratings should be equal", new_rating, movie.getRating());
    }
}