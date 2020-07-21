package core.repository;

import core.domain.Client;
import core.domain.Movie;

import java.util.List;

public interface MovieRepositoryCustom {
    List<Movie> findAllByIdWithRentalsAndClientJPQL(long id);
    List<Movie> findAllWithRentalsAndClientJPQL();
    List<Movie> findAllByIdWithRentalsAndClientCriteriaAPI(long id);
    List<Movie> findAllWithRentalsAndClientCriteriaAPI();
    List<Movie> findAllByIdWithRentalsAndClientSQL(long id);
    List<Movie> findAllWithRentalsAndClientSQL();
}
