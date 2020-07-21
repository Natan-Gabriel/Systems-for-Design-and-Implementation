package core.repository;

import core.domain.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JPARentalRepository extends Repository<Rental,Long>  {
    public void deleteByClientID(long id);
    public void deleteByMovieID(long id);
    //public void findByRentalSerialNumber(String s);
    Page<Rental> findAll(Pageable firstPageWithTwoElements);
}
