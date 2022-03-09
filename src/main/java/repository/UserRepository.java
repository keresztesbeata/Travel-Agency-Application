package repository;

import model.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserRepository {
    private static UserRepository instance;
    private static final String SQL_QUERY_FIND_USER_BY_USERNAME = "select user from User user where user.username = ?1";

    private UserRepository() {}

    public static UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User findByUsername(String username) {
        EntityManager entityManager = EntityRepository.getEntityManager();
        entityManager.getTransaction().begin();
        User user = null;
        List<User> users = entityManager
                .createQuery(SQL_QUERY_FIND_USER_BY_USERNAME)
                .setParameter(1, username)
                .getResultList();
        if(!users.isEmpty()) {
            user = users.get(0);
        }
        entityManager.close();
        return user;
    }

    public void save(User user) {
        EntityManager entityManager = EntityRepository.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
