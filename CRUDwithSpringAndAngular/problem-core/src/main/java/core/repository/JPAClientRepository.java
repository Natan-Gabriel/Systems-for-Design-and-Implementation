package core.repository;

import core.domain.Client;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Set;

public interface JPAClientRepository extends Repository<Client,Long> {
    //public boolean existsByID(long id);
    List<Client>  findAllByAge(int age, Pageable p);
    //List<Client>  findAll( Pageable p);
    List<Client> findAllByName(String s, Pageable p);
}
