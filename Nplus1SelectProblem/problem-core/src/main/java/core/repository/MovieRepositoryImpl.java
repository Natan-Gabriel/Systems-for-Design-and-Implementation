package core.repository;

import core.domain.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class MovieRepositoryImpl extends CustomRepositorySupport implements MovieRepositoryCustom {
    @Override
    public List<Movie> findAllWithRentalsAndClientJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select distinct a from Movie a " +
                "inner join fetch a.rentals b " +
                "inner join fetch b.client");
        List<Movie> movies = query.getResultList();

        return movies;
    }
    @Override
    public List<Movie> findAllByIdWithRentalsAndClientJPQL(long id) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select a from Movie a " +
                "inner join fetch a.rentals b " +
                "inner join fetch b.client "+
                "where a.id = " + id);
        List<Movie> movies = query.getResultList();

        return movies;
    }


    @Override
    public List<Movie> findAllWithRentalsAndClientCriteriaAPI() {
        //criteria
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
        query.distinct(Boolean.TRUE);
        Root<Movie> root = query.from(Movie.class);
        //query.select(root);
        Fetch<Movie, ClientMovie> clientRentalFetch = root.fetch(Movie_.rentals);
        clientRentalFetch.fetch(ClientMovie_.client);
        return entityManager.createQuery(query).getResultList();
    }
    @Override
    public List<Movie> findAllByIdWithRentalsAndClientCriteriaAPI(long id){
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
        query.distinct(Boolean.TRUE);
        Root<Movie> root = query.from(Movie.class);
        //query.select(root);
        Predicate condition = criteriaBuilder.equal(root.get(Movie_.id), id);
        query.where(condition);
        Fetch<Movie, ClientMovie> clientRentalFetch = root.fetch(Movie_.rentals);
        clientRentalFetch.fetch(ClientMovie_.client);
        return entityManager.createQuery(query).getResultList();

    }


    @Override
    @Transactional
    public List<Movie> findAllWithRentalsAndClientSQL() {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {a.*},{b.*},{p.*} " +
                "from movie a " +
                "inner join client_movie b on a.id=b.movie_id " +
                "inner join client p on b.client_id=p.id ")
                .addEntity("a",Movie.class)
                .addJoin("b", "a.rentals")
                .addJoin("p", "b.client")
                .addEntity("a",Movie.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Movie> movies = query.getResultList();


        return movies;
    }
    @Override
    @Transactional
    public List<Movie> findAllByIdWithRentalsAndClientSQL(long id) {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {a.*},{b.*},{p.*} " +
                "from movie a " +
                "inner join client_movie b on a.id=b.movie_id " +
                "inner join client p on b.client_id=p.id "+
                "where a.id = :given_id ")
                .addEntity("a",Movie.class)
                .addJoin("b", "a.rentals")
                .addJoin("p", "b.client")
                .setParameter("given_id", id)
                .addEntity("a",Movie.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        //query.setParameter("given_id", id);
        List<Movie> clients = query.getResultList();


        return clients;
    }

}

