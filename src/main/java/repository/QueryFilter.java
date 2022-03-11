package repository;

import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class QueryFilter<T> {
    protected EntityManager entityManager;
    protected CriteriaBuilder criteriaBuilder;
    protected CriteriaQuery<T> criteriaQuery;
    protected Root<T> root;
    private List<Predicate> predicates;
    private Class<T> type;

    public QueryFilter(EntityManager entityManager, Class<T> type) {
        this.type = type;
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(type);
        root = criteriaQuery.from(type);
        this.predicates = new ArrayList<>();
    }

    public void addPredicate(Predicate predicate) {
        predicates.add(predicate);
    }

    public void removePredicate(Predicate predicate) {
        predicates.remove(predicate);
    }

    public List<T> applyFilter() {
        if(predicates.size()>1) {
            criteriaQuery.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }else if(predicates.size()>0){
            criteriaQuery.select(root).where(predicates.get(0));
        }
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
