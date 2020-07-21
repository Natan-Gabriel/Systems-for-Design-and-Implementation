package core.repository;

import core.domain.Client;
import core.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JPAMovieRepository extends Repository<Movie,Long> {
    List<Movie> findAllByName(String s, Pageable firstPageWithElements);
    Page<Movie> findAll(Pageable firstPageWithTwoElements);
}
