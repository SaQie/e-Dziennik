package pl.edziennik.eDziennik.server.personinformation.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.personinformation.PersonInformation;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.TypedQuery;
@Repository
class PersonInformationDaoImpl extends BaseDao<PersonInformation> implements PersonInformationDao{

    @Override
    public boolean isPeselAlreadyExist(String pesel) {
        TypedQuery<PersonInformation> query = em.createNamedQuery(Queries.GET_PRESON_INFORMATION_BY_PESEL, PersonInformation.class);
        query.setParameter(Parameters.PESEL, pesel);
        return PersistanceHelper.isObjectExist(query);
    }


    private static final class Queries{

        private static final String GET_PRESON_INFORMATION_BY_PESEL = "PersonInformation.getPersonInformationByPesel";

    }

    private static final class Parameters{

        private static final String PESEL = "pesel";

    }
}
