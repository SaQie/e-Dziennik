package pl.edziennik.eDziennik.server.user.dao;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.user.domain.User;

public interface UserDao extends IBaseDao<User> {

    User getByUsername(String username);

    User getByEmail(String email);

    boolean isUserExistByUsername(String username);

    boolean isUserExistByEmail(String email);

}
