import domain.Entities.Client;
import domain.Entities.Movie;
import domain.Entities.Rental;
import domain.Validators.ValidatorException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    private IClientService clientService;
    private IMovieService movieService;
    private IRentalService rentalService;

    public ClientApp()
    {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "config"
                );

        clientService = context.getBean(IClientService.class);
        movieService = context.getBean(IMovieService.class);
        rentalService = context.getBean(IRentalService.class);

    }

    public void run() {
        while (true) {
            System.out.println("Input command:\n1.Add a client\n2.Add a movie\n3.Delete a client\n4.Delete a movie\n5.Update a client\n6.Update a movie\n" +
                    "7.Print all clients\n8.Print all movies\n9.Rent movie.\n10.Return movie.\n11.Delete rental.\n12.Update rental.\n" +
                    "13.Print all rentals.\n14.Filter movies by name\n15.Filter clients by name\n16.Get most rented movie\n17.Get most active client\n18.Get client with most returned movies\n" +
                    "19.# of clients without movies - # of movies without clients=? (are there more clients without movies than movies without clients?)\n"
                    + "20.Print sorted movies\n21.Print sorted clients\n22.Print sorted rentals\n0.Exit");
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            try {
                int selection = Integer.parseInt(bufferRead.readLine());
                switch (selection) {
                    case 1:
                        addClient(bufferRead);
                        break;
                    case 2:
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
                        break;
                    case 0:
                        bufferRead.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid command!\n");
                }
            } catch (ValidatorException e) {
                e.printStackTrace();
            } catch (IOException io) {
                System.out.println("Something went wrong when reading inputs!");
            } catch (NumberFormatException nf) {
                System.out.println("Invalid number!");
            } catch (ClassNotFoundException nf) {
                System.out.println("ClassNotFound");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void printAllClients() throws IOException, ClassNotFoundException, SQLException {
        CompletableFuture<Set<Client>> com = CompletableFuture.supplyAsync(()-> {
            try {
                return clientService.getAllClients();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        com.thenAcceptAsync(s -> s.stream().forEach(System.out::println));
    }

    private void printAllMovies() throws IOException, ClassNotFoundException, SQLException {
        CompletableFuture<Set<Movie>> com = CompletableFuture.supplyAsync(()-> {
            try {
                return movieService.getAllMovies();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        com.thenAcceptAsync(s -> s.stream().forEach(System.out::println));

    }

    private void sortMovies() throws Exception {
        CompletableFuture<List<Movie>> com = CompletableFuture.supplyAsync(()-> {
            try {
                return movieService.getAllMoviesSorted();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        com.thenAcceptAsync(s -> s.stream().forEach(System.out::println));
    }

    private void sortClients() throws Exception {
        CompletableFuture<List<Client>> com = CompletableFuture.supplyAsync(()-> {
            try {
                return clientService.getAllClientsSorted();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        com.thenAcceptAsync(s -> s.stream().forEach(System.out::println));
    }

    private void sortRentals() throws Exception {
        CompletableFuture<List<Rental>> com = CompletableFuture.supplyAsync(()-> {
            try {
                return rentalService.getAllRentalsSorted();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        com.thenAcceptAsync(s -> s.stream().forEach(System.out::println));
    }

    private void addClient(BufferedReader buf)
    {
        try{
            System.out.println("Insert client name:");
            String name = buf.readLine();
            System.out.println("Insert client age: ");
            int age = Integer.parseInt(buf.readLine());
            System.out.println("Insert client serial number: ");
            String serial = buf.readLine();
            System.out.println("Insert client id: ");
            long id = Long.valueOf(buf.readLine());

            Client cl = new Client(name, age, serial);
            cl.setId(id);

            CompletableFuture.runAsync(()-> {
                try {
                    clientService.addClient(cl);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMovie(BufferedReader buf)
    {
        try{
            System.out.println("Insert movie name:");
            String name = buf.readLine();
            System.out.println("Insert movie year: ");
            int year = Integer.parseInt(buf.readLine());
            System.out.println("Insert movie serial number: ");
            String serial = buf.readLine();
            System.out.println("Insert movie rating: ");
            int rating = Integer.parseInt(buf.readLine());
            System.out.println("Insert movie id: ");
            long id = Long.valueOf(buf.readLine());

            Movie mv = new Movie(name, year, rating, serial);
            mv.setId(id);

            CompletableFuture.runAsync(()-> {
                try {
                    movieService.addMovie(mv);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deleteClient(BufferedReader buf)
    {
        try{
            System.out.println("Insert client id: ");
            long id = Long.valueOf(buf.readLine());

            CompletableFuture.runAsync(()-> {
                try {
                    rentalService.deleteClient(id);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMovie(BufferedReader buf)
    {
        try{
            System.out.println("Insert movie id: ");
            long id = Long.valueOf(buf.readLine());

            CompletableFuture.runAsync(()-> {
                try {
                    rentalService.deleteMovie(id);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateClient(BufferedReader buf)
    {
        try{
            System.out.println("Insert client name:");
            String name = buf.readLine();
            System.out.println("Insert client age: ");
            int age = Integer.parseInt(buf.readLine());
            System.out.println("Insert client serial number: ");
            String serial = buf.readLine();
            System.out.println("Insert client id: ");
            long id = Long.valueOf(buf.readLine());

            Client cl = new Client(name, age, serial);
            cl.setId(id);

            CompletableFuture.runAsync(()-> {
                try {
                    clientService.updateClient(cl);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMovie(BufferedReader buf)
    {
        try{
            System.out.println("Insert movie name:");
            String name = buf.readLine();
            System.out.println("Insert movie year: ");
            int year = Integer.parseInt(buf.readLine());
            System.out.println("Insert movie serial number: ");
            String serial = buf.readLine();
            System.out.println("Insert movie rating: ");
            int rating = Integer.parseInt(buf.readLine());
            System.out.println("Insert movie id: ");
            long id = Long.valueOf(buf.readLine());

            Movie mv = new Movie(name, year, rating, serial);
            mv.setId(id);

            CompletableFuture.runAsync(()-> {
                try {
                    movieService.updateMovie(mv);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printAllRentals() throws IOException, ClassNotFoundException, SQLException {
        CompletableFuture<Set<Rental>> com = CompletableFuture.supplyAsync(()-> {
            try {
                return rentalService.getAllRentals();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        com.thenAcceptAsync(s -> s.stream().forEach(System.out::println));
    }

    private void rentMovie(BufferedReader buf)
    {
        try{
            System.out.println("Insert rental serial number: ");
            String rentalSerial = buf.readLine();
            System.out.println("Insert movie ID: ");
            long movieID = Long.valueOf(buf.readLine());
            System.out.println("Insert client ID: ");
            long clientID = Long.valueOf(buf.readLine());
            System.out.println("Insert rental id: ");
            long id = Long.valueOf(buf.readLine());

            Rental ren = new Rental(rentalSerial, movieID, clientID);
            ren.setId(id);

            CompletableFuture.runAsync(()-> {
                try {
                    rentalService.addRental(ren);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deleteRental(BufferedReader buf)
    {
        try{
            System.out.println("Insert rental id: ");
            long id = Long.valueOf(buf.readLine());

            CompletableFuture.runAsync(()-> {
                try {
                    rentalService.deleteRental(id);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateRental(BufferedReader buf)
    {
        try{
            System.out.println("Insert rental serial number: ");
            String rentalSerial = buf.readLine();
            System.out.println("Insert movie ID: ");
            long movieID = Long.valueOf(buf.readLine());
            System.out.println("Insert client ID: ");
            long clientID = Long.valueOf(buf.readLine());
            System.out.println("Insert rental id: ");
            long id = Long.valueOf(buf.readLine());

            Rental ren = new Rental(rentalSerial, movieID, clientID);
            ren.setId(id);

            CompletableFuture.runAsync(()-> {
                try {
                    rentalService.updateRental(ren);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnMovie(BufferedReader buf) {
        try {
            System.out.println("Insert rental serial number: ");
            String rentalSerial = buf.readLine();

            CompletableFuture.runAsync(()-> {
                try {
                    rentalService.returnMovie(rentalSerial);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void filterMoviesByName(BufferedReader buf)
    {
        try{
            System.out.println("Insert filter");
            String filter = buf.readLine();

            CompletableFuture<Set<Movie>> com = CompletableFuture.supplyAsync(()-> {
                try {
                    return this.movieService.filterMoviesByName(filter);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });
            com.thenAcceptAsync(s -> s.stream().forEach(System.out::println));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterClientsByName(BufferedReader buf)
    {
        try{
            System.out.println("Insert filter");
            String filter = buf.readLine();
            CompletableFuture<Set<Client>> com = CompletableFuture.supplyAsync(()-> {
                try {
                    return this.clientService.filterClientsByName(filter);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });
            com.thenAcceptAsync(s -> s.stream().forEach(System.out::println));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getMostRentedMovie() {
        try {
            CompletableFuture<List<Long>> com = CompletableFuture.supplyAsync(() -> {
                try {
                    return this.rentalService.getMostRentedMovie();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });
            com.thenAcceptAsync(s -> s.stream().forEach(System.out::println));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getMostActiveClient() {
        try {
            CompletableFuture<List<Long>> com = CompletableFuture.supplyAsync(() -> {
                try {
                    return this.rentalService.getMostActiveClient();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });
            com.thenAcceptAsync(s -> s.stream().forEach(System.out::println));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getClientWithMostReturned() {
        try {
            CompletableFuture<List<Long>> com = CompletableFuture.supplyAsync(() -> {
                try {
                    return this.rentalService.getClientWithMostReturned();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });
            com.thenAcceptAsync(s -> s.stream().forEach(System.out::println));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void computeClientsMinusBooks() {
        try {
            CompletableFuture<Integer> com = CompletableFuture.supplyAsync(() -> {
                try {
                    return this.rentalService.computeClientsMinusBooks();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });
            com.thenAcceptAsync(s -> System.out.println(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
