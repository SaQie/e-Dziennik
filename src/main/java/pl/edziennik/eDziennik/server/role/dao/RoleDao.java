package pl.edziennik.eDziennik.server.role.dao;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.role.domain.Role;

import java.util.Optional;

public interface RoleDao extends IBaseDao<Role> {

    Optional<Role> findByName(String name);

}
