package core.repository;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Getter
public abstract class CustomRepositorySupport {
    @PersistenceContext
    private EntityManager entityManager;
}

