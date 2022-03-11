package repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

    public void delete(L id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        T existingEntity = entityManager.find(type, id);
        entityManager.remove(existingEntity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public T findById(L id) {
        EntityManager entityManager = getEntityManager();
        return entityManager.find(type, id);
    }

    public List<T> findAll() {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        List<T> result = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.close();

        return result;
    }

    public T update(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        T updatedEntity = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        entityManager.close();

        return updatedEntity;
    }

    protected List<T> findByCriteria(QueryFilter<T> queryFilter) {
        return queryFilter.applyFilter();
    }
}

