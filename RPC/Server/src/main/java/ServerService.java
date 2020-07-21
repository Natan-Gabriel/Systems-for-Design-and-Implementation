import TCP.Service;
import domain.Movie;
import domain.Rental;
import service.ClientService;
import domain.Client;
import service.MovieService;
import service.RentalService;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ServerService implements Service {
    private ClientService clientService;
    private MovieService movieService;
    private RentalService rentalService;
    private ExecutorService executorService;

    public ServerService(ClientService cs, MovieService ms, RentalService rs, ExecutorService es)
    {
        clientService=cs;
        movieService=ms;
        rentalService=rs;
        executorService=es;
    }

    @Override
    public Future<String> communicate(String head, String body) {
        String[] selections = body.split(",");
        int selection = Integer.parseInt(head);
        switch (selection) {
            default:
                return executorService.submit(() -> "Invalid command");
            case 1:
                Client c = new Client(selections[0], Integer.parseInt(selections[1]), selections[2]);
                c.setId(Long.parseLong(selections[3]));
                executorService.submit(() -> {
                    try {
                        clientService.addClient(c);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                return executorService.submit(() -> "Success");
            case 2:
                Movie m = new Movie(selections[0], Integer.parseInt(selections[1]), Integer.parseInt(selections[3]), selections[2]);
                m.setId(Long.parseLong(selections[4]));
                executorService.submit(() -> {
                    try {
                        movieService.addMovie(m);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                return executorService.submit(() -> "Success");
            case 3:
                executorService.submit(() -> {
                    try {
                        rentalService.deleteClient(Long.parseLong(selections[0]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                return executorService.submit(() -> "Success");
            case 4:
                executorService.submit(() -> {
                    try {
                        rentalService.deleteMovie(Long.parseLong(selections[0]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                return executorService.submit(() -> "Success");
            case 5:
                Client c1 = new Client(selections[0], Integer.parseInt(selections[1]), selections[2]);
                c1.setId(Long.parseLong(selections[3]));
                executorService.submit(() -> {
                    try {
                        clientService.updateClient(c1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                return executorService.submit(() -> "Success");
            case 6:
                Movie m1 = new Movie(selections[0], Integer.parseInt(selections[1]), Integer.parseInt(selections[3]), selections[2]);
                m1.setId(Long.parseLong(selections[4]));
                executorService.submit(() -> {
                    try {
                        movieService.updateMovie(m1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                return executorService.submit(() -> "Success!");
            case 7:
                return executorService.submit(() -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        return clientService.getAllClients().get().toString();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
            case 8:
                return executorService.submit(() -> movieService.getAllMovies().get().toString());
            case 9:
                Rental r = new Rental(selections[0], Long.parseLong(selections[1]), Long.parseLong(selections[2]));
                r.setId(Long.parseLong(selections[3]));
                executorService.submit(() -> {
                    try {
                        rentalService.addRental(r);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                return executorService.submit(() -> "Success");
            case 10:
                executorService.submit(() -> {
                    try {
                        rentalService.returnMovie(selections[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                return executorService.submit(() -> "Success");
            case 11:
                executorService.submit(() -> {
                    try {
                        rentalService.deleteRental(Long.parseLong(selections[0]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                return executorService.submit(() -> "Success");
            case 12:
                Rental r1 = new Rental(selections[0], Long.parseLong(selections[1]), Long.parseLong(selections[2]));
                r1.setId(Long.parseLong(selections[3]));
                executorService.submit(() -> {
                    try {
                        rentalService.updateRental(r1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                return executorService.submit(() -> "Success");
            case 13:
                return executorService.submit(() -> rentalService.getAllRentals().get().toString());
            case 14:
                return executorService.submit(() -> movieService.filterMoviesByName(selections[0]).get().toString());
            case 15:
                return executorService.submit(() -> clientService.filterClientsByName(selections[0]).get().toString());
            case 16:
                return executorService.submit(() -> rentalService.getMostRentedMovie().get().toString());
            case 17:
                return executorService.submit(() -> rentalService.getMostActiveClient().get().toString());
            case 18:
                return executorService.submit(() -> rentalService.getClientWithMostReturned().get().toString());
            case 19:
                return executorService.submit(() -> rentalService.computeClientsMinusBooks().get().toString());
            case 20:
                return executorService.submit(() -> movieService.getAllMoviesSorted().get().toString());
            case 21:
                return executorService.submit(() -> clientService.getAllClientsSorted().get().toString());
            case 22:
                return executorService.submit(() -> rentalService.getAllRentalsSorted().get().toString());
            /*case 2:
                addMovie(bufferRead);
                break;
            case 3:
                deleteClient(bufferRead);
                break;
            case 4:
                deleteMovie(bufferRead);
                break;
            case 5:
                updateClient(bufferRead);
                break;
            case 6:
                updateMovie(bufferRead);
                break;
            case 7:
                printAllClients();
                break;
            case 8:
                printAllMovies();
                break;
            case 9:
                rentMovie(bufferRead);
                break;
            case 10:
                returnMovie(bufferRead);
                break;
            case 11:
                deleteRental(bufferRead);
                break;
            case 12:
                updateRental(bufferRead);
                break;
            case 13:
                printAllRentals();
                break;
            case 14:
                filterMoviesByName(bufferRead);
                break;
            case 15:
                filterClientsByName(bufferRead);
                break;
            case 16:
                getMostRentedMovie();
                break;
            case 17:
                getMostActiveClient();
                break;
            case 18:
                getClientWithMostReturned();
                break;
            case 19:
                computeClientsMinusBooks();
                break;
            case 20:
                sortMovies();
                break;
            case 21:
                sortClients();
                break;
            case 22:
                sortRentals();
                break;*/
        }
    }
}
