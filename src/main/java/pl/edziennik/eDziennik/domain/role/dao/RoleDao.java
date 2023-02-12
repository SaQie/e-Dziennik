package pl.edziennik.eDziennik.domain.role.dao;

import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;
import pl.edziennik.eDziennik.domain.role.domain.Role;

import java.util.Optional;

public interface RoleDao extends IBaseDao<Role> {

    Optional<Role> findByName(String name);

}
