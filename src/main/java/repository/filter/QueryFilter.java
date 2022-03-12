package repository.filter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class QueryFilter<T> {
    protected EntityManager entityManager;
    protected CriteriaBuilder criteriaBuilder;
    protected CriteriaQuery<T> criteriaQuery;
    protected Root<T> root;
    private Class<T> type;
    private Predicate predicate;

    public QueryFilter(EntityManager entityManager, Class<T> type) {
        this.type = type;
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(type);
        root = criteriaQuery.from(type);
        this.predicate = criteriaBuilder.and();
    }

    protected void addPredicate(Predicate newPredicate) {
        predicate = criteriaBuilder.and(this.predicate, newPredicate);
    }

    public void resetFilter() {
        predicate = criteriaBuilder.and();
    }

    public List<T> applyFilter() {
        criteriaQuery.select(root).where(predicate);
        List<T> result = entityManager.createQuery(criteriaQuery).getResultList();
        resetFilter();
        return result;
    }
}
