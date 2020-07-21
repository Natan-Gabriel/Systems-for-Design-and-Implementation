package service;

import domain.Entities.Rental;
import domain.Validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class RentalService implements IRentalService{
    @Autowired
    IRentalService rentalService;

    @Override
    public void addRental(Rental rental) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        rentalService.addRental(rental);
    }

    @Override
    public void updateRental(Rental rental) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        rentalService.updateRental(rental);
    }

    @Override
    public void deleteRental(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        rentalService.deleteRental(id);
    }

    @Override
    public void deleteClient(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        rentalService.deleteClient(id);
    }

    @Override
    public void deleteMovie(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        rentalService.deleteMovie(id);
    }

    @Override
    public Set<Rental> getAllRentals() throws IOException, ClassNotFoundException, SQLException {
        return rentalService.getAllRentals();
    }

    @Override
    public void returnMovie(String s) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        rentalService.returnMovie(s);
    }

    @Override
    public List<Long> getMostRentedMovie() throws IOException, ClassNotFoundException, SQLException {
        return rentalService.getMostRentedMovie();
    }

    @Override
    public List<Long> getMostActiveClient() throws IOException, ClassNotFoundException, SQLException {
        return rentalService.getMostActiveClient();
    }

    @Override
    public List<Long> getClientWithMostReturned() throws IOException, ClassNotFoundException, SQLException {
        return rentalService.getClientWithMostReturned();
    }

    @Override
    public List<Long> getBestClientIDFromList(List<Rental> result) throws IOException, ClassNotFoundException {
        return rentalService.getBestClientIDFromList(result);
    }

    @Override
    public int computeClientsMinusBooks() throws IOException, ClassNotFoundException, SQLException {
        return rentalService.computeClientsMinusBooks();
    }

    @Override
    public List<Rental> getAllRentalsSorted() throws Exception {
        return rentalService.getAllRentalsSorted();
    }
}
