package core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import core.domain.Client;
import core.domain.Movie;
import core.domain.Rental;
import core.domain.Validators.RentalValidator;
import core.domain.Validators.Validator;
import core.domain.Validators.ValidatorException;
import core.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class RentalService {

    public static final Logger log = LoggerFactory.getLogger(MovieService.class);
    @Autowired
    private JPARentalRepository rentalRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private MovieService movieService;

    //@Autowired
    //private RentalValidator rentalValidator;
    private Validator<Rental> rentalValidator=new RentalValidator();



    /**
     * adds a rental
     *
     * @param rental a rental
     *
     *
     *@throws ValidatorException
     *            if the entity is not valid.
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/
    public Rental addRental(Rental rental) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        log.trace("addRental - method entered: rental={}", rental);
        //if (!movieService.findOne(rental.getMovieID()).isPresent())
        if (!movieService.findOne(rental.getMovieID()).isPresent())
            throw new ValidatorException("Invalid movie ID!");
        if(!clientService.findOne(rental.getClientID()).isPresent())
            throw new ValidatorException("Invalid client ID!");
        rentalValidator.validate(rental);
        rentalRepository.save(rental);
        log.trace("addRental - method finished");
        return rental;
    }

    /**
     * updates a rental
     *
     * @param rental a rental
     *
     *
     *@throws ValidatorException
     *            if the entity is not valid.
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/
    @Transactional
    public Rental updateRental(long id,Rental rental) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        log.trace("updateRental - method entered: rental={}", rental);
        if (!movieService.existsByID(rental.getMovieID())) {
            log.trace("updateRental - ERROR - movieId is invalid");
            throw new ValidatorException("Invalid movie ID!");
        }
        if(!clientService.existsByID(rental.getClientID())){
            log.trace("updateRental - ERROR - clientId is invalid");
            throw new ValidatorException("Invalid client ID!");
        }
        rentalValidator.validate(rental);
        //rentalRepository.update(rental);
        rentalRepository.findById(id)
                .ifPresent(s -> {
                    s.setRentalSerialNumber(rental.getRentalSerialNumber());
                    s.setMovieID(rental.getMovieID());
                    s.setClientID(rental.getClientID());
                    s.setReturned(rental.isReturned());
                    log.debug("updateRental - updated: s={}", s);
                });
        log.trace("updateRental - method finished");
        return rental;
    }

    /**
     * deletes a rental
     *
     * @param id of a rental
     *
     *
     *@throws ValidatorException
     *            if the entity is not valid.
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/
    public void deleteRental(long id) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        log.trace("deleteRental - method entered: id={}",id);
        try {
            rentalRepository.deleteById(id);
            log.trace("deleteRental - method finished");
        }
        catch(Exception e) {
            log.trace("deleteRental - there is no rental with given id");
            throw new ValidatorException("Invalid rental ID!");
        }
    }

    /**
     * deletes clients with given id from rentalRepository
     *  we don't have to validate again the given entity because it is validated when it is removed
     *  from its repository(cascade deleting is called in console after deleting the given client)
     *
     * @param id id of a rental
     *
     *
     *@throws ValidatorException
     *            if the entity is not valid.
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/

    public void deleteClient(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //pre:give an id
        //post:if entities is not empty and entities contains Rental objects,remove clients with given id from rentals
        log.trace("deleteClient - method entered: id={}",id);
        try {
            this.clientService.deleteClient(id);
            this.rentalRepository.deleteByClientID(id);
            log.trace("deleteClient - method finished");
        }
        catch(Exception e) {
            log.trace("deleteClient-ERROR -there is no client with given id ");
        }

    }


    /**
     * deletes movies with given id from rentalRepository
     *  we don't have to validate again the given entity because it is validated when it is removed
     *  from its repository(cascade deleting is called in console after deleting the given client)
     *
     * @param id id of a rental
     *
     *
     *@throws ValidatorException
     *            if the entity is not valid.
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/
    public void deleteMovie(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        //pre:give an id
        //post:if entities is not empty and entities contains Rental objects,remove movies with given id from rentals
        log.trace("deleteMovie - method entered: id={}",id);
        try {
            this.movieService.deleteMovie(id);
            this.rentalRepository.deleteByMovieID(id);
            log.trace("deleteMovie - method finished");
        }
        catch(Exception e) {
            log.trace("deleteMovie-ERROR -there is no movie with given id ");
        }
    }

    /**
     * returns a set of all rentals
     *
     * @return returns set of all rentals
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public Set<Rental> getAllRentals() throws IOException, ClassNotFoundException, SQLException {
        log.trace("getAllRentals - method entered");
        Iterable<Rental> rentals = rentalRepository.findAll();
        log.trace("getAllRentals - method finished");
        return StreamSupport.stream(rentals.spliterator(), false).collect(Collectors.toSet());
    }

    public Optional<Rental> findOne(long id) {
        log.trace("findOne - method entered: id={}", id);
        //Movie movie=this.movieRepository.getOne(id);
        Optional<Rental> rental=this.rentalRepository.findById(id);
        log.trace("findOne - method finished");
        return rental;
    }

    /**
     * pre:receives a string
     * post:updates the movies with given serial number
     *
     * @param id serial number
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/
    @Transactional
    public void returnMovie(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        log.trace("returnMovie - method entered: id={}", id);
        rentalRepository.findById(id)
                .ifPresent(elem -> {
                    elem.setReturned(true);
                    log.debug("returnMovie - updated: s={}", elem);
                });
        log.trace("returnMovie - method finished");
    }

    /**
     *
     *
     * @return list of most rented movies
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public List<Long> getMostRentedMovie() throws IOException, ClassNotFoundException, SQLException {
        log.trace("getMostRentedMovie - method entered");
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Rental> result = new ArrayList<Rental>();
        rentals.forEach(result::add);

        List<Long> resultFinal=result.stream().map(s->s.getMovieID()).collect(Collectors.toList());

        long maxNumberofOcc = result.stream()
                .collect(Collectors.groupingBy(Rental::getMovieID, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue).orElse(Long.valueOf(-1));

        resultFinal.removeIf(rental -> Collections.frequency(resultFinal, rental)!=(int)(maxNumberofOcc));
        List<Long> listWithoutDuplicates = resultFinal.stream().distinct().collect(Collectors.toList());
        log.trace("getMostRentedMovie - method finished");
        return listWithoutDuplicates;
    }
    /**
     *
     * @return list of most active clients
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public List<Client> getMostActiveClient() throws IOException, ClassNotFoundException {
        log.trace("getMostActiveClient - method entered");
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Rental> result = new ArrayList<Rental>();
        rentals.forEach(a->result.add(a));
        log.trace("getMostActiveClient - method entered");
        List<Long> res=getBestClientIDFromList(result);
        List<Client> result2 = new ArrayList<Client>();
        res.forEach(a->result2.add(clientService.findOne(a).get()));
        return result2;
    }

    /**
     *
     * @return list of clients with most returned movies
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public List<Long> getClientWithMostReturned() throws IOException, ClassNotFoundException, SQLException {
        log.trace("getClientWithMostReturned - method entered");
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Rental> result = new ArrayList<Rental>();
        rentals.forEach(result::add);
        result.removeIf(s->s.isReturned()==false);
        log.trace("getClientWithMostReturned - method finished");
        return getBestClientIDFromList(result);
    }

    /**
     *
     * @param result a list of rentals
     *
     * @return returns clientIDs with most appearances in given list;if list in empty we return an empty list
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public List<Long> getBestClientIDFromList(List<Rental> result) throws IOException, ClassNotFoundException {
        List<Long> resultFinal = result.stream().map(s -> s.getClientID()).collect(Collectors.toList());

        long maxNumberofOcc = result.stream()
                .collect(Collectors.groupingBy(Rental::getClientID, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue).orElse(Long.valueOf(-1));

        resultFinal.removeIf(rental -> Collections.frequency(resultFinal, rental) != (int) (maxNumberofOcc));
        List<Long> listWithoutDuplicates = resultFinal.stream().distinct().collect(Collectors.toList());

        return listWithoutDuplicates;
    }

    /**
     *
     * @return # of clients without movies - # of movies without clients=? (are there more clients without movies than movies without clients?)
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     **/
    public int computeClientsMinusBooks() throws IOException, ClassNotFoundException, SQLException {
        log.trace("computeClientsMinusBooks - method entered");
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Rental> result = new ArrayList<Rental>();
        rentals.forEach(result::add);
        List<Long> resultOfClients = result.stream().map(s -> s.getClientID()).collect(Collectors.toList());
        List<Long> resultOfMovies = result.stream().map(s -> s.getMovieID()).collect(Collectors.toList());


        Iterable<Client> clients = clientService.getAllClients();
        List<Client> listOfClients = new ArrayList<Client>();
        clients.forEach(listOfClients::add);
        List<Long> listOfClients1 = listOfClients.stream().map(s -> s.getId()).collect(Collectors.toList());

        Iterable<Movie> movies = movieService.getAllMovies();
        List<Movie> listOfMovies = new ArrayList<Movie>();
        movies.forEach(listOfMovies::add);
        List<Long> listOfMovies1 = listOfMovies.stream().map(s -> s.getId()).collect(Collectors.toList());

        listOfClients1.removeAll(resultOfClients);
        listOfMovies1.removeAll(resultOfMovies);
        log.trace("computeClientsMinusBooks - method finished");
        return listOfClients1.size()-listOfMovies1.size();
    }

    public List<Rental> getAllRentalsSorted(long size,long page,String field,String direction)  {
        //returns a set of all movies
        log.trace("getAllRentalsSorted - method entered");
        //List<Rental> sorted = rentalRepository.findAll(Sort.by(Sort.Direction.ASC, "rentalSerialNumber"));
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

        Page<Rental> sorted = rentalRepository.findAll(firstPageWithTwoElements);
        log.trace("getAllRentalsSorted - method finished");
        return sorted.getContent();
    }


}