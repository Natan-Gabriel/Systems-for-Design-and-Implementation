package service;

import domain.Entities.Client;
import domain.Validators.ValidatorException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IClientService {
    public void addClient(Client client) throws ValidatorException, ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException;
    public Set<Client> getAllClients() throws IOException, ClassNotFoundException, SQLException;
    public Client filterClientsBySerial(String s) throws IOException, ClassNotFoundException, SQLException;
    public Optional<Client> findOne(long id) throws IOException, ClassNotFoundException, SQLException;
    public Set<Client> filterClientsByName(String s) throws IOException, ClassNotFoundException, SQLException;
    public void deleteClient(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;
    public void updateClient(Client client) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;
    public List<Client> getAllClientsSorted() throws Exception;
}
