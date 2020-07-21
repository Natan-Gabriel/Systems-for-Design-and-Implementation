package core.repository;

import core.domain.Client;
import core.domain.ClientMovie;

import java.util.List;

public interface ClientRepositoryCustom {
    List<Client> findAllByIdWithRentalsAndMovieJPQL(long id);
    List<Client> findAllWithRentalsAndMovieJPQL();
    List<Client> findAllByIdWithRentalsAndMovieCriteriaAPI(long id);
    List<Client> findAllWithRentalsAndMovieCriteriaAPI();
    List<Client> findAllByIdWithRentalsAndMovieSQL(long id);
    List<Client> findAllWithRentalsAndMovieSQL();
}
