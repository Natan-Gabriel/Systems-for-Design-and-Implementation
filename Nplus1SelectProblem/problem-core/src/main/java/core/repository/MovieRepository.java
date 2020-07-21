package core.repository;

import core.domain.Client;
import core.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends Repository<Movie,Long>,MovieRepositoryCustom {
    List<Movie> findAllByName(String s, Pageable firstPageWithElements);
    Page<Movie> findAll(Pageable firstPageWithTwoElements);
    @Query("select distinct a from Movie a where a.name = ?1")
    @EntityGraph(value = "movieWithRentalsAndClient", type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> findDistinctByName(String name);

    @Query("select distinct a from Movie a where a.serialNumber = ?1")
    @EntityGraph(value = "movieWithRentalsAndClient", type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> findDistinctBySerial(String serial);
}
