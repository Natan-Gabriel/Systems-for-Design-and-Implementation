

import TCP.Message;
import TCP.Service;
import repository.Repository;
import repository.DBClientRepository;
import repository.DBMovieRepository;
import repository.DBRentalRepository;
import TCP.TCPServer;

import domain.Client;
import domain.Movie;
import domain.Rental;
import service.ClientService;
import service.MovieService;
import service.RentalService;
import domain.Validators.ClientValidator;
import domain.Validators.MovieValidator;
import domain.Validators.RentalValidator;
import domain.Validators.Validator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ServerApp {

    public static void main(String[] args) {
        Validator<Client> clientValidator = new ClientValidator();
        Validator<Movie> movieValidator = new MovieValidator();
        Validator<Rental> rentalValidator = new RentalValidator();

        Repository<Long, Client> clientRepository = new DBClientRepository(clientValidator);
        Repository<Long, Movie> movieRepository = new DBMovieRepository(movieValidator);
        Repository<Long, Rental> rentalRepository = new DBRentalRepository(rentalValidator);

        System.out.println("server started");
        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        ClientService clientService = new ClientService(clientRepository, executorService);
        MovieService movieService = new MovieService(movieRepository, executorService);
        RentalService rentalService = new RentalService(rentalRepository, movieService, clientService, executorService);


        Service service = new ServerService(clientService, movieService, rentalService, executorService);

        TCPServer tcpServer = new TCPServer(executorService);

        for (int i = 0; i <= 22; i++) {
            tcpServer.addHandler(Integer.toString(i), (request) -> {
                String body = request.getBody();
                String head = request.getHeader();
                Future<String> future = service.communicate(head, body);
                try {
                    String result = future.get();
                    return new Message("ok", result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());
                }

            });
        }

        tcpServer.startServer();

        executorService.shutdown();
    }
}
