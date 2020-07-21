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
import org.springframework.jdbc.core.JdbcOperations;
import repository.repos.DBClientRepository;
import repository.repos.DBMovieRepository;
import repository.repos.DBRentalRepository;
import repository.repos.SortingRepository;

@Configuration
public class RepoConfig {
    @Autowired
    JdbcOperations jdbcOperations;

    @Bean
    SortingRepository<Long, Client> clientRepository() {
        Validator<Client> clientValidator = new ClientValidator();
        return new DBClientRepository(clientValidator, jdbcOperations);
    }

    @Bean
    SortingRepository<Long, Movie> movieRepository(){
        Validator<Movie> movieValidator = new MovieValidator();
        return new DBMovieRepository(movieValidator, jdbcOperations);
    }

    @Bean
    SortingRepository<Long, Rental> rentalRepository(){
        Validator<Rental> rentalValidator = new RentalValidator();
        return new DBRentalRepository(rentalValidator, jdbcOperations);
    }
}
