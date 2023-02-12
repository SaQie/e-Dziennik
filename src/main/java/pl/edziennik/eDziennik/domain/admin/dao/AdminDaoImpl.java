package pl.edziennik.eDziennik.domain.admin.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.server.basics.dao.BaseDao;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.TypedQuery;

@Repository
class AdminDaoImpl extends BaseDao<Admin> implements AdminDao{

    public Admin getByUsername(String username){
        TypedQuery<Admin> query = em.createNamedQuery(Queries.GET_ADMIN_BY_USERNAME, Admin.class);
        query.setParameter(Parameters.USERNAME, username);
        return (Admin) PersistanceHelper.getSingleResultOrNull(query);
    }


    private static final class Parameters {

        private static final String USERNAME = "username";

    }

    private static final class Queries {

        private static final String GET_ADMIN_BY_USERNAME = "Admin.getAdminByUsername";

    }


}
