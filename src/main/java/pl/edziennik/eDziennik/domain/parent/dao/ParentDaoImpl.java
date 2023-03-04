package pl.edziennik.eDziennik.domain.parent.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.server.basics.dao.BaseDao;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.TypedQuery;

@Repository
class ParentDaoImpl extends BaseDao<Parent> implements ParentDao {


    @Override
    public boolean isParentExistsByPesel(String pesel) {
        TypedQuery<Parent> query = em.createNamedQuery(Queries.GET_PARENT_BY_PESEL, Parent.class);
        query.setParameter(Parameters.PESEL, pesel);
        return PersistanceHelper.isObjectExist(query);
    }


    private static final class Queries {

        private static final String GET_PARENT_BY_PESEL = "Parent.getParentByPesel";

    }

    private static final class Parameters {

        private static final String PESEL = "pesel";

    }
}
