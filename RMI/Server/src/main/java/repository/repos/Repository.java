package repository.repos;

import domain.Entities.Entity;
import domain.Validators.ValidatorException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;


public interface Repository<ID, T extends Entity<ID>> {
    /**
     * Find the entity with the given {@code id}.
     *
     * @param id
     *            must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IOException if the are problems working with the fle
     * @throws IllegalArgumentException
     *             if the given id is null.
     * @throws ClassNotFoundException if we have problems reading/writing an object
     *
     */
    Optional<T> findOne(ID id) throws IOException, ClassNotFoundException, SQLException;

    /**
     *
     * @return all entities.
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     */
    Iterable<T> findAll() throws IOException, ClassNotFoundException, SQLException;

    void setAll(Map<ID, T> m) throws ClassNotFoundException, ParserConfigurationException, TransformerException, IOException, SQLException;

    /**
     * Saves the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     */
    Optional<T> save(T entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;

    /**
     * Removes the entity with the given id.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException
     *             if the given id is null.
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     */
    Optional<T> delete(ID id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;

    /**
     *does cascade deleting
     * if entities is not empty and entities contains Rental objects,remove clients with given id from rentals
    *
     * @param id of a client,it is not null if we reached this function
    *
     *

     * @throws IOException if we have problems working with the file,
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     ClassNotFoundException if we have problems reading/writing an object

     **/
    //public void deleteClient(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException;


    /**
     *does cascade deleting
     * if entities is not empty and entities contains Rental objects,remove movies with given id from rentals

     * @param id of a client,it is not null if we reached this function



     * @throws IOException if we have problems working with the file,
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *      *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     **/
   // public void deleteMovie(long id) throws IOException, ClassNotFoundException, TransformerException, ParserConfigurationException;


    /**
     * Updates the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     *         entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws IOException if the are problems working with the fle
     * @throws ClassNotFoundException if we have problems reading/writing an object
     * @throws TransformerException if the current ErrorListoner determines to
     *                               throw an exception.
     * @throws ParserConfigurationException if DocumentBuilderFactory or the DocumentBuilder s it creates cannot support the feature
     *
     */
    Optional<T> update(T entity) throws ValidatorException, IOException, ClassNotFoundException, TransformerException, ParserConfigurationException, SQLException;

    Map<ID, T> getAll() throws IOException, ClassNotFoundException, SQLException;
}
