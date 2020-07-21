package service;

import domain.Entities.Client;
import domain.Entities.Movie;
import domain.Entities.Rental;
import domain.Validators.RentalValidator;
import domain.Validators.ValidatorException;
import repository.repos.DBRentalRepository;
import repository.repos.Repository;
import repository.repos.SortingRepository;
import repository.impl.Sort;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RentalService implements IRentalService{
    private Repository<Long, Rental> rentalRepository;
    private MovieService movieService;
    private ClientService clientService;
    private RentalValidator rentalValidator;


    public RentalService(Repository<Long, Rental> new_rentalRepository, MovieService movieService1, ClientService clientService1){
        this.rentalRepository=new_rentalRepository;
        this.movieService=movieService1;
        this.clientService=clientService1;
        rentalValidator=new RentalValidator();
    }

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
    public void addRental(Rental rental) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        if (!movieService.findOne(rental.getMovieID()).isPresent())
            throw new ValidatorException("Invalid movie ID!");
        if(!clientService.findOne(rental.getClientID()).isPresent())
            throw new ValidatorException("Invalid client ID!");
        rentalValidator.validate(rental);
        rentalRepository.save(rental);
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
    public void updateRental(Rental rental) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        if (!movieService.findOne(rental.getMovieID()).isPresent())
            throw new ValidatorException("Invalid movie ID!");
        if(!clientService.findOne(rental.getClientID()).isPresent())
            throw new ValidatorException("Invalid client ID!");
        rentalValidator.validate(rental);
        rentalRepository.update(rental);
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
        if (!rentalRepository.findOne(id).isPresent())
            throw new ValidatorException("Invalid rental ID!");
        rentalRepository.delete(id);
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
        this.clientService.deleteClient(id);
        Map<Long, Rental> entities = this.rentalRepository.getAll();
        if (!entities.isEmpty()){
            entities.values().removeIf(s->s.getClientID()==id);
        }
        this.rentalRepository.setAll(entities);
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
        this.movieService.deleteMovie(id);
        Map<Long, Rental> entities = this.rentalRepository.getAll();
        if (!entities.isEmpty()){
            entities.values().removeIf(s->s.getMovieID()==id);
        }
        this.rentalRepository.setAll(entities);
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
        Iterable<Rental> rentals = rentalRepository.findAll();
        return StreamSupport.stream(rentals.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * pre:receives a string
     * post:updates the movies with given serial number
     *
     * @param s serial number
     *
     *
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/
    public void returnMovie(String s) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException {
        Iterable<Rental> rentals = rentalRepository.findAll();
        Set<Rental> filteredRentals= new HashSet<>();
        rentals.forEach(filteredRentals::add);
        filteredRentals.removeIf(rental -> !rental.getRentalSerial().equals(s));
        Rental ren = filteredRentals.iterator().next();
        ren.returnMovie();
        rentalRepository.update(ren);
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
    public List<Long> getMostActiveClient() throws IOException, ClassNotFoundException, SQLException {
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Rental> result = new ArrayList<Rental>();
        rentals.forEach(result::add);

        return getBestClientIDFromList(result);
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
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<Rental> result = new ArrayList<Rental>();
        rentals.forEach(result::add);
        result.removeIf(s->s.isReturned()==false);

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

        return listOfClients1.size()-listOfMovies1.size();
    }

    public List<Rental> getAllRentalsSorted() throws Exception {
        //returns a set of all movies
        Sort sort = new Sort(true, "movieID");

        if (rentalRepository instanceof SortingRepository) {
            SortingRepository<Long, Rental> rentalRepository1 = (DBRentalRepository) rentalRepository;
            Iterable<Rental> rentals = rentalRepository1.findAll(sort);
            return StreamSupport.stream(rentals.spliterator(), false).collect(Collectors.toList());
        } else {
            throw new Exception("Invalid repo!");
        }
    }


}