package repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class EntityRepository<T, L> {
    private static final String PERSISTENCE_UNIT_NAME = "main";
    private Class<T> type;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public EntityRepository(Class<T> type) {
        this.type = type;
        this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    protected EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    public void save(T entity) {
        EntityManager entityManager = getEntityManager();
        beginTransaction();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void delete(L id) {
        EntityManager entityManager = getEntityManager();
        beginTransaction();
        T existingEntity = entityManager.find(type, id);
        entityManager.remove(existingEntity);
        entityManager.getTransaction().commit();
    }

    public T findById(L id) {
        EntityManager entityManager = getEntityManager();
        return entityManager.find(type, id);
    }

    public List<T> findAll() {
        EntityManager entityManager = getEntityManager();
        beginTransaction();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        List<T> result = entityManager.createQuery(criteriaQuery).getResultList();

        return result;
    }

    public T update(T entity) {
        EntityManager entityManager = getEntityManager();
        beginTransaction();
        T updatedEntity = entityManager.merge(entity);
        entityManager.getTransaction().commit();

        return updatedEntity;
    }

    protected void beginTransaction() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }
}

