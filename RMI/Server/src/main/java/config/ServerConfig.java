package config;

import domain.Entities.Client;
import domain.Entities.Movie;
import domain.Entities.Rental;
import domain.Validators.ClientValidator;
import domain.Validators.MovieValidator;
import domain.Validators.RentalValidator;
import domain.Validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import repository.impl.Sort;
import repository.repos.*;
import service.*;

@Configuration
public class ServerConfig {
    @Autowired
    SortingRepository<Long, Client> clientRepository;

    @Autowired
    SortingRepository<Long, Movie> movieRepository;

    @Autowired
    SortingRepository<Long, Rental> rentalRepository;

    @Bean
    RmiServiceExporter rmiClientServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("ClientService");
        rmiServiceExporter.setServiceInterface(IClientService.class);
        rmiServiceExporter.setService(clientService());
        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter rmiRentalServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("RentalService");
        rmiServiceExporter.setServiceInterface(IRentalService.class);
        rmiServiceExporter.setService(rentalService());
        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter rmiMovieServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("MovieService");
        rmiServiceExporter.setServiceInterface(IMovieService.class);
        rmiServiceExporter.setService(movieService());
        return rmiServiceExporter;
    }

    @Bean
    IMovieService movieService() {
        return new MovieService(movieRepository);
    }

    @Bean
    IRentalService rentalService()
    {
        ClientService clientService = new ClientService(clientRepository);
        MovieService movieService = new MovieService(movieRepository);
        return new RentalService(rentalRepository, movieService, clientService);
    }

    @Bean
    IClientService clientService()
    {
        return new ClientService(clientRepository);
    }
}
