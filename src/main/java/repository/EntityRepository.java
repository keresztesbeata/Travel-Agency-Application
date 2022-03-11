package repository;

import model.User;
import model.VacationPackage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class EntityRepository<T, L> {
    private static final String PERSISTENCE_UNIT_NAME = "main";
    private Class<T> type;
    private EntityManagerFactory entityManagerFactory;

    public EntityRepository(Class<T> type) {
        this.type = type;
        this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void save(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void delete(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public T findById(L id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        T entity = entityManager.find(type, id);
        entityManager.close();
        return entity;
    }

    public List<T> findAll() {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        Query query = entityManager.createQuery(criteriaQuery);
        List<T> result = (List<T>) query.getResultList();
        entityManager.close();
        return result;
    }

    public void update(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    protected List<T> findByCriteria(List<Predicate> predicates) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root).where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)));
        Query query = entityManager.createQuery(criteriaQuery);
        List<T> result = (List<T>) query.getResultList();
        entityManager.close();
        return result;
    }

}

