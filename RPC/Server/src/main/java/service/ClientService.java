package service;

import domain.Client;
import domain.Validators.ValidatorException;
import repository.DBClientRepository;
import repository.Repository;
import repository.SortingRepository;
import repository.impl.Sort;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientService {
    private Repository<Long, Client> clientRepository;
    private ExecutorService executorService;

    public ClientService(Repository<Long, Client> new_clientRepository, ExecutorService executorService){
        this.clientRepository=new_clientRepository;
        this.executorService = executorService;
    }

    public void addClient(Client client) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //adds a client
        clientRepository.save(client);
    }

    public Future<Set<Client>> getAllClients() throws IOException, ClassNotFoundException, SQLException {
        //returns a set of all clients
        Iterable<Client> clients = clientRepository.findAll();
        return executorService.submit(() -> StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet()));
    }

    public Future<Client> filterClientsBySerial(String s) throws IOException, ClassNotFoundException, SQLException {
        //pre:receives a string
        //post:deletes all clients that don't have the given serial number
        Iterable<Client> clients = clientRepository.findAll();
        Set<Client> filteredClients= new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client -> !client.getSerial().equals(s));

        return executorService.submit(() -> filteredClients.iterator().next());
    }

    public Future<Optional<Client>> findOne(long id) throws IOException, ClassNotFoundException, SQLException {
        return executorService.submit(() -> this.clientRepository.findOne(id));
    }

    public Future<Set<Client>> filterClientsByName(String s) throws IOException, ClassNotFoundException, SQLException {
        Iterable<Client> clients = clientRepository.findAll();
        Set<Client> filteredClients = new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client -> !client.getName().contains(s));
        return executorService.submit(() -> filteredClients);
    }

    public void deleteClient(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //deletes a client
        clientRepository.delete(id);
    }
    public void updateClient(Client client) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //updates a client
        clientRepository.update(client);
    }

    public Future<List<Client>> getAllClientsSorted() throws Exception {
        //returns a set of all movies
        Sort sort = new Sort(false, "name");

        if (clientRepository instanceof SortingRepository) {
            SortingRepository<Long, Client> clientRepository1 = (DBClientRepository) clientRepository;
            Iterable<Client> clients = clientRepository1.findAll(sort);
            return executorService.submit(() -> StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toList()));
        } else {
            throw new Exception("Invalid repo!");
        }
    }
}