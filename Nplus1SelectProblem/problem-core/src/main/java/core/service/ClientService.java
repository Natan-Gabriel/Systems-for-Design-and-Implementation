package core.service;

import core.domain.ClientMovie;
import core.domain.Movie;
import core.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import core.repository.ClientRepository;
//import core.repository.impl.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {//implements IClientService{
    public static final Logger log = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieRepository movieRepository;

    @Value("SQL")
    private String jdbcUrl;

    private Validator<Client> clientValidator=new ClientValidator();

    public List<Client> getClientMovies(long clientId){
        if(jdbcUrl=="SQL") {
            return clientRepository.findAllByIdWithRentalsAndMovieSQL(clientId);
        }
        if(jdbcUrl=="CriteriaAPI") {
            return clientRepository.findAllByIdWithRentalsAndMovieCriteriaAPI(clientId);
        }
        else
            return clientRepository.findAllByIdWithRentalsAndMovieJPQL(clientId);
    }
    public List<Client> getClientsWithRentalsAndMovies()
    {
        if(jdbcUrl=="SQL") {
            return clientRepository.findAllWithRentalsAndMovieSQL();
        }
        if(jdbcUrl=="CriteriaAPI") {
            return clientRepository.findAllWithRentalsAndMovieCriteriaAPI();
        }
        else
            return clientRepository.findAllWithRentalsAndMovieJPQL();
    }
    public List<Client> findDistinctByName(String name){
        return clientRepository.findDistinctByName(name);
    }
    public List<Client> findDistinctBySerial(String name){
        return clientRepository.findDistinctBySerial(name);
    }

    public List<ClientMovie> getAllRentals() throws IOException, ClassNotFoundException, SQLException {
        //returns a set of all clients
        log.trace("getAllRentals - method entered");
        List<Client> clients = clientRepository.findAllWithRentalsAndMovie();
        List<ClientMovie> rentals=new ArrayList<ClientMovie>();
        clients.forEach(a -> {
            rentals.addAll(a.getRentals());
        });

        log.trace("getAllRentals - method finished");
        return rentals;//StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }
    //    public ClientMovie addRental(ClientMovie rental) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
//        log.trace("addRental - method entered: rental={}", rental);
//        //if (!movieService.findOne(rental.getMovieID()).isPresent())
////        if (!movieService.findOne(rental.getMovieID()).isPresent())
////            throw new ValidatorException("Invalid movie ID!");
////        if(!clientService.findOne(rental.getClientID()).isPresent())
////            throw new ValidatorException("Invalid client ID!");
////        rentalValidator.validate(rental);
//        Set<ClientMovie> l=rental.getClient().getRentals();
//        l.add(rental);
//        rental.getClient().setRentals(l);
//        log.trace("addRental - method finished");
//        return rental;
//    }
    @Transactional
    public void addRental(Long clientId, Long movieId,String serial) throws SQLException, IOException, ClassNotFoundException {
        log.trace("addRental --- method entered! - movieId {}, clientId {}", movieId, clientId);
        //Client client = clientService.findOne(clientId).get();
        System.out.println(clientId);
        System.out.println(movieId);
        System.out.println(serial);
        Client client = clientRepository.getOne(clientId);
//        Client client=null;
//        try {
//            client = clientRepository.findAllByIdWithRentalsAndMovieJPQL(clientId).get(0);
//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
        Movie movie = movieRepository.getOne(movieId);
        //Movie movie = movieService.findOne(movieId).get();
        client.getRentals().add(new ClientMovie(clientRepository.getOne(clientId),movieRepository.getOne(movieId),serial,true));
        client
                .getRentals()
                .add(new ClientMovie(client,movie,serial,true));
        log.trace("addRental: method exit");
    }
    @Transactional
    public void deleteRental(Long clientId, Long movieId) throws SQLException, IOException, ClassNotFoundException {
        log.trace("addRental --- method entered! - movieId {}, clientId {}", movieId, clientId);
        clientRepository.findAllByIdWithRentalsAndMovieJPQL(clientId).forEach(client ->
                client.getRentals().stream()
                        .filter(rental -> rental.getMovie().getId().equals(movieId))
                        .forEach(clientRental -> System.out.println(clientRental))
        );
        //Method method = this.getDeclaredMethod("getAllRentals");
        Client cl=clientRepository.getOne(clientId);
        List<ClientMovie> l=cl.getRentals().stream().filter(rental -> rental.getMovie().getId().equals(movieId)).collect(Collectors.toList());

        System.out.println("l is"+l);
        for (ClientMovie cm:l){
            cl.getRentals().remove(cm);
        }



//        Movie m=movieRepository.findAllByIdWithRentalsAndClientJPQL(movieId).get(0);
//        System.out.println("cl before"+cl.getRentals());
//        ClientMovie cm1=cl.getRentals().stream().filter(rental -> rental.getClient().getId().equals(clientId)).toArray(ClientMovie[]::new)[0];
//        System.out.println("cm"+cm);
//        Set<ClientMovie> res1 = cl.getRentals();
//        System.out.println("res befoer"+res);
//        res.remove(cm1);
//        System.out.println("res after"+res);
//        cl.setRentals(res1);


        //System.out.println("cl after"+getAllRentals());
        log.trace("addRental: method exit");
    }

    @Transactional
    public void updateRental(Long clientId, Long movieId, String serial,boolean b) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        log.trace("updateRental - method entered: rental={}");
        clientRepository.findAllByIdWithRentalsAndMovieJPQL(clientId)
                .forEach(m ->
                        m.getRentals()
                                .stream()
                                .filter(r -> r.getMovie().getId().equals(movieId))
                                .forEach(r -> {r.setRentalSerialNumber(serial);r.setReturned(b);})

                );
        log.trace("updateRental - method finished");
        //return rental;
    }

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