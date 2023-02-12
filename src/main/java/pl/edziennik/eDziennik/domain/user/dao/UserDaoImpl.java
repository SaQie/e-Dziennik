package pl.edziennik.eDziennik.domain.user.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.basics.dao.BaseDao;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.TypedQuery;

@Repository
class UserDaoImpl extends BaseDao<User> implements UserDao {


    @Override
    public User getByUsername(String username) {
        TypedQuery<User> query = em.createNamedQuery(Queries.GET_USER_BY_USERNAME, User.class);
        query.setParameter(Parameters.USERNAME, username);
        return (User) PersistanceHelper.getSingleResultOrNull(query);
    }

    @Override
    public User getByEmail(String email) {
        TypedQuery<User> query = em.createNamedQuery(Queries.GET_USER_BY_EMAIL, User.class);
        query.setParameter(UserDaoImpl.Parameters.EMAIL, email);
        return (User) PersistanceHelper.getSingleResultOrNull(query);
    }

    @Override
    public boolean isUserExistByUsername(String username) {
        TypedQuery<User> query = em.createNamedQuery(Queries.GET_USER_BY_USERNAME, User.class);
        query.setParameter(Parameters.USERNAME, username);
        return PersistanceHelper.isObjectExist(query);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        TypedQuery<User> query = em.createNamedQuery(Queries.GET_USER_BY_EMAIL, User.class);
        query.setParameter(UserDaoImpl.Parameters.EMAIL, email);
        return PersistanceHelper.isObjectExist(query);
    }

    private static final class Parameters{

        private static final String USERNAME = "username";
        private static final String EMAIL = "email";

    }

    private static final class Queries{

        private static final String GET_USER_BY_EMAIL = "User.getByEmail";
        private static final String GET_USER_BY_USERNAME = "User.getByUsername";

    }


}
