package core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import core.domain.Client;
import core.domain.Validators.ClientValidator;
import core.domain.Validators.Validator;
import core.domain.Validators.ValidatorException;
import core.repository.JPAClientRepository;
//import core.repository.impl.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClientService {//implements IClientService{
    public static final Logger log = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    private JPAClientRepository clientRepository;

    //public static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private Validator<Client> clientValidator=new ClientValidator();

    /*public ClientService(Repository<Long, Client> new_clientRepository){
        this.clientRepository=new_clientRepository;
    }*/

    public Client addClient(Client client) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //adds a client
        clientValidator.validate(client);
        log.trace("addClient - method entered: client={}", client);
        clientRepository.save(client);
        log.trace("addClient - method finished: client={}",client);
        return client;
    }
    public boolean existsByID(long id){
        return clientRepository.existsById(id);
    }

    public List<Client> getAllClients() throws IOException, ClassNotFoundException, SQLException {
        //returns a set of all clients
        log.trace("getAllClients - method entered");
        List<Client> clients = clientRepository.findAll();
        log.trace("getAllClients - method finished");
        return clients;//StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }

    public Client filterClientsBySerial(String s) throws IOException, ClassNotFoundException, SQLException {
        //pre:receives a string
        //post:deletes all clients that don't have the given serial number
        log.trace("filterClientsBySerial - method entered:filter={}",s);
        Iterable<Client> clients = clientRepository.findAll();
        Set<Client> filteredClients= new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client -> !client.getSerialNumber().equals(s));
        log.trace("filterClientsBySerial - method finished");
        return filteredClients.iterator().next();
    }

    public Optional<Client> findOne(long id) {//throws IOException, ClassNotFoundException, SQLException {
        log.trace("findOne - method entered:id={}",id);
        //Client cl= new Client(this.clientRepository.getOne(id).getSerialNumber(),this.clientRepository.getOne(id).getName(),this.clientRepository.getOne(id).getAge());
        //cl.setId(id);
        Optional<Client> cl=this.clientRepository.findById(id);
        log.trace("findOne - method finished",id);
        //return Optional.of(cl);
        return cl;
    }

    public List<Client> filterClientsByName(String s,long size,long page)  {
        log.trace("filterClientsByName - method entered:filter={}",s);
        /*Iterable<Client> clients = clientRepository.findAll();
        Set<Client> filteredClients = new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(client -> !client.getName().contains(s));*/



        Pageable firstPageWithElements = PageRequest.of((int)page, (int)size);

        List<Client> allClients =
                clientRepository.findAllByName(s, firstPageWithElements);

        log.trace("filterClientsByName - method finished",s);
        return allClients;
    }

    public void deleteClient(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //deletes a client
        log.trace("deleteClient - method entered:id={}",id);
        clientRepository.deleteById(id);
        log.trace("deleteClient - method finished",id);
    }
    @Transactional
    public Client updateClient(Long id,Client client) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //updates a client
        log.trace("updateClient - method entered:client={}",client);
        if (!existsByID(client.getId())) {
            log.trace("updateClient - ERROR - clientId is invalid");
            throw new ValidatorException("Invalid client ID!");
        }
        clientValidator.validate(client);
        clientRepository.findById(client.getId())
                .ifPresent(s -> {
                    s.setName(client.getName());
                    s.setAge(client.getAge());
                    s.setSerialNumber(client.getSerialNumber());
                    log.debug("updateClient - updated: s={}", s);
                });
        log.trace("updateClient - method finished",client);
        return client;
    }

    public List<Client> getAllClientsSorted(long size,long page,String field,String direction) {
        //returns a set of all movies
        //Sort sort = new Sort(false, "name");
        log.trace("getAllClientsSorted - method entered");
        System.out.println(direction);
        Sort.Direction dir;
        if (direction.equals("DESC"))
        {
            dir=Sort.Direction.DESC;
            System.out.println("desc");
        }
        else {
            dir = Sort.Direction.ASC;
            System.out.println("asc");
        }

        Pageable firstPageWithTwoElements = PageRequest.of((int)page, (int)size,Sort.by(dir,field));

        Page<Client> allClients = clientRepository.findAll(firstPageWithTwoElements);

        //List<Client> sorted = clientRepository.findAll(Sort.by(dir, field));
        log.trace("getAllClientsSorted - method finished");
        return allClients.getContent();
    }
    public List<Client> getAllClientsSortedandPaginated(long size,long page) {
        //returns a set of all movies
        //Sort sort = new Sort(false, "name");
        log.trace("getAllClientsSorted - method entered");
//        List<Client> sorted = clientRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
//        Pageable sortedByPriceDescNameAsc =
//                PageRequest.of((int)page, 5, Sort.by("name").descending().and(Sort.by("name")));

//        Pageable secondPageWithFiveElements = PageRequest.of((int)page, 5);
//        List<Client> allClients =
//                clientRepository.findAllByAge(51, secondPageWithFiveElements);

        Pageable firstPageWithTwoElements = PageRequest.of((int)page, (int)size,Sort.by("name").descending());

        Page<Client> allClients = clientRepository.findAll(firstPageWithTwoElements);

        log.trace("getAllClientsSorted - method finished");
        return allClients.getContent();
    }
}