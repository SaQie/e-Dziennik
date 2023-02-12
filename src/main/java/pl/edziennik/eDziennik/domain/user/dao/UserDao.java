package pl.edziennik.eDziennik.domain.user.dao;

import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;
import pl.edziennik.eDziennik.domain.user.domain.User;

public interface UserDao extends IBaseDao<User> {

    User getByUsername(String username);

    User getByEmail(String email);

    boolean isUserExistByUsername(String username);

    boolean isUserExistByEmail(String email);

}
