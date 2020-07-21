package repository.repos;

import domain.Entities.Entity;
import repository.impl.Sort;


import java.io.Serializable;

public interface SortingRepository<ID extends Serializable, T extends Entity<ID>> extends Repository<ID, T>{
    Iterable<T> findAll(Sort sort) throws Exception;
}
