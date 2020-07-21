package service;

import domain.Entities.Rental;
import domain.Validators.ValidatorException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IRentalService {
    public void addRental(Rental rental) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;
    public void updateRental(Rental rental) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;
    public void deleteRental(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;
    public void deleteClient(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;
    public void deleteMovie(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;
    public Set<Rental> getAllRentals() throws IOException, ClassNotFoundException, SQLException;
    public void returnMovie(String s) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;
    public List<Long> getMostRentedMovie() throws IOException, ClassNotFoundException, SQLException;
    public List<Long> getMostActiveClient() throws IOException, ClassNotFoundException, SQLException;
    public List<Long> getClientWithMostReturned() throws IOException, ClassNotFoundException, SQLException;
    public List<Long> getBestClientIDFromList(List<Rental> result) throws IOException, ClassNotFoundException;
    public int computeClientsMinusBooks() throws IOException, ClassNotFoundException, SQLException;
    public List<Rental> getAllRentalsSorted() throws Exception;
}
