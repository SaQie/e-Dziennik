package pl.edziennik.eDziennik.domain.role.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.basics.dao.BaseDao;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
class RoleDaoImpl extends BaseDao<Role> implements RoleDao {


    @Override
    public Optional<Role> findByName(String name) {
        TypedQuery<Role> query = em.createNamedQuery("Role.getRoleByName", Role.class);
        query.setParameter(Parameters.NAME, name);
        return PersistanceHelper.getSingleResultAsOptional(query);
    }

    private final static class Parameters{

        private static final String NAME = "name";

    }

}
