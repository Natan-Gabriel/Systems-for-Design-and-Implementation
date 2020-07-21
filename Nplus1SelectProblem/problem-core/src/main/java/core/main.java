package core;

import core.domain.Client;
import core.domain.Movie;
import core.service.ClientService;
import core.service.MovieService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("core.config");

        ClientService authorService = context.getBean(ClientService.class);
        List<Client> all = authorService.getClientsWithRentalsAndMovies();
        //List<Client> all = authorService.getClientMovies(2);
        all.forEach(s->System.out.println(s.getRentals()));

//        MovieService authorService = context.getBean(MovieService.class);
        //List<Movie> all = authorService.getMovieClients(1);
//        List<Movie> all = authorService.getMoviesWithRentalsAndClients();
//        all.forEach(s->System.out.println(s));

        //authorService.addRental((long)4,(long)3,"10");
        //authorService.updateRental((long)4,(long)4,"99",false);
        //System.out.println(authorService.getAllRentals());
        authorService.deleteRental((long)4,(long)3);
        //authorService.deleteMovie(4);
        //System.out.println(authorService.getAllRentals());





//        Publisher publisher=new ArrayList<>(all.get(0).getBooks()).get(0).getPublisher();
//        System.out.println(publisher);

    }
}
