package service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RentalServiceTest {
//    private RentalService service;
//    private Validator<Rental> rentalValidator;
//    private InMemoryRepository repository;
//    private String rentalSerialNumber ="1234";
//    private String movieSerialNumber = "123";
//    private String movieName = "Cats";
//    private String clientSerialNumber = "234";
//    private String clientName = "Cat";
//    private boolean returned = false;
//    private long id = 10000;
//
//    @Before
//    public void setUp() throws Exception {
//        rentalValidator = new RentalValidator();
//        repository = new InMemoryRepository(rentalValidator);
//        service = new RentalService(repository);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        rentalValidator=null;
//        repository=null;
//        service=null;
//    }
//
//    @Test
//    public void testAddRental() throws Exception{
//        Rental ren = new Rental(rentalSerialNumber, movieSerialNumber, movieName, clientSerialNumber, clientName);
//        ren.setId(id);
//        service.addRental(ren);
//        assertEquals("should be equal", ren, service.getAllRentals().toArray()[0]);
//    }
//
//    @Test
//    public void testGetAllRentals() throws Exception{
//        Rental ren = new Rental(rentalSerialNumber, movieSerialNumber, movieName, clientSerialNumber, clientName);
//        ren.setId(id);
//        service.addRental(ren);
//        Rental ren2 = new Rental("12345", "12341", "Dogs", "48856", "Dog");
//        ren2.setId((long) 9585);
//        service.addRental(ren2);
//        assertEquals("should be equal", ren, service.getAllRentals().toArray()[0]);
//        assertEquals("should be equal", ren2, service.getAllRentals().toArray()[1]);
//    }
//
//    @Test
//    public void testReturnMovie() throws Exception{
//        Rental ren = new Rental(rentalSerialNumber, movieSerialNumber, movieName, clientSerialNumber, clientName);
//        ren.setId(id);
//        service.addRental(ren);
//        service.returnMovie(rentalSerialNumber);
//        ren.returnMovie();
//        assertEquals("should be equal", ren, service.getAllRentals().toArray()[0]);
//    }
}
