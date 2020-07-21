package core.repository;

import core.domain.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl extends CustomRepositorySupport implements ClientRepositoryCustom {
    @Override
    public List<Client> findAllWithRentalsAndMovieJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select distinct a from Client a " +
                "inner join fetch a.rentals b " +
                "inner join fetch b.movie");
        List<Client> authors = query.getResultList();

        return authors;
    }
    @Override
    public List<Client> findAllByIdWithRentalsAndMovieJPQL(long id) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select a from Client a " +
                "inner join fetch a.rentals b " +
                "inner join fetch b.movie "+
                "where a.id = " + id);
        List<Client> clients = query.getResultList();

        return clients;
    }


    @Override
    public List<Client> findAllWithRentalsAndMovieCriteriaAPI() {
        //criteria
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = criteriaBuilder.createQuery(Client.class);
        query.distinct(Boolean.TRUE);
        Root<Client> root = query.from(Client.class);
        //query.select(root);
        Fetch<Client, ClientMovie> movieRentalFetch = root.fetch(Client_.rentals);
        movieRentalFetch.fetch(ClientMovie_.movie);
        return entityManager.createQuery(query).getResultList();
    }
    @Override
    public List<Client> findAllByIdWithRentalsAndMovieCriteriaAPI(long id){
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = criteriaBuilder.createQuery(Client.class);
        query.distinct(Boolean.TRUE);
        Root<Client> root = query.from(Client.class);
        //query.select(root);
        Predicate condition = criteriaBuilder.equal(root.get(Client_.id), id);
        query.where(condition);
        Fetch<Client, ClientMovie> movieRentalFetch = root.fetch(Client_.rentals);
        movieRentalFetch.fetch(ClientMovie_.movie);
        return entityManager.createQuery(query).getResultList();

    }


    @Override
    @Transactional
    public List<Client> findAllWithRentalsAndMovieSQL() {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {a.*},{b.*},{p.*} " +
                "from client a " +
                "inner join client_movie b on a.id=b.client_id " +
                "inner join movie p on b.movie_id=p.id ")
                .addEntity("a",Client.class)
                .addJoin("b", "a.rentals")
                .addJoin("p", "b.movie")
                .addEntity("a",Client.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Client> authors = query.getResultList();


        return authors;
    }
    @Override
    @Transactional
    public List<Client> findAllByIdWithRentalsAndMovieSQL(long id) {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {a.*},{b.*},{p.*} " +
                "from client a " +
                "inner join client_movie b on a.id=b.client_id " +
                "inner join movie p on b.movie_id=p.id "+
                "where a.id = :given_id ")
                .addEntity("a",Client.class)
                .addJoin("b", "a.rentals")
                .addJoin("p", "b.movie")
                .setParameter("given_id", id)
                .addEntity("a",Client.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        //query.setParameter("given_id", id);
        List<Client> authors = query.getResultList();


        return authors;
    }

}
