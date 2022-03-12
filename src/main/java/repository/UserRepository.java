package repository;

import model.User;

import java.util.List;

public class UserRepository extends EntityRepository<User, Long> {
    private static UserRepository instance;
    private static final String SQL_QUERY_FIND_USER_BY_USERNAME = "select user from User user left join fetch user.vacationPackages where user.username = ?1";

    private UserRepository() {
        super(User.class);
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User findByUsername(String username) {
        User user = null;
        List<User> users = getEntityManager()
                .createQuery(SQL_QUERY_FIND_USER_BY_USERNAME)
                .setParameter(1, username)
                .getResultList();
        if (!users.isEmpty()) {
            user = users.get(0);
        }
        return user;
    }


}
