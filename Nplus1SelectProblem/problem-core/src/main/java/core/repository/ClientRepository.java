package core.repository;

import core.domain.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Set;

public interface ClientRepository extends Repository<Client,Long>,ClientRepositoryCustom {
    //public boolean existsByID(long id);
    List<Client>  findAllByAge(int age, Pageable p);
    //List<Client>  findAll( Pageable p);
    List<Client> findAllByName(String s, Pageable p);
    @Query("select distinct a from Client a")
    @EntityGraph(value = "clientWithRentals", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllWithRentals();

    @Query("select distinct a from Client a")
    @EntityGraph(value = "clientWithRentalsAndMovie", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllWithRentalsAndMovie();

    @Query("select distinct a from Client a where a.name = ?1")
    @EntityGraph(value = "clientWithRentalsAndMovie", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findDistinctByName(String publisherName);

    @Query("select distinct a from Client a where a.serialNumber = ?1")
    @EntityGraph(value = "clientWithRentalsAndMovie", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findDistinctBySerial(String publisherName);
}
