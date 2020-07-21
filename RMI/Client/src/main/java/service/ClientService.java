package service;

import domain.Entities.Client;
import domain.Validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ClientService implements IClientService{
    @Autowired
    private IClientService clientService;


    @Override
    public void addClient(Client client) throws ValidatorException, ClassNotFoundException, ParserConfigurationException, TransformerException, SQLException, IOException {
        clientService.addClient(client);
    }

    @Override
    public Set<Client> getAllClients() throws IOException, ClassNotFoundException, SQLException {
        return clientService.getAllClients();
    }

    @Override
    public Client filterClientsBySerial(String s) throws IOException, ClassNotFoundException, SQLException {
        return clientService.filterClientsBySerial(s);
    }

    @Override
    public Optional<Client> findOne(long id) throws IOException, ClassNotFoundException, SQLException {
        return clientService.findOne(id);
    }

    @Override
    public Set<Client> filterClientsByName(String s) throws IOException, ClassNotFoundException, SQLException {
        return clientService.filterClientsByName(s);
    }

    @Override
    public void deleteClient(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        clientService.deleteClient(id);
    }

    @Override
    public void updateClient(Client client) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        clientService.updateClient(client);
    }

    @Override
    public List<Client> getAllClientsSorted() throws Exception {
        return clientService.getAllClientsSorted();
    }
}
