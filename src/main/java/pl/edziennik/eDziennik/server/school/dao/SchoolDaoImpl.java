package pl.edziennik.eDziennik.server.school.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.TypedQuery;

@Repository
class SchoolDaoImpl extends BaseDao<School> implements SchoolDao{

    @Override
    public boolean isSchoolExist(String schoolName) {
        TypedQuery<School> query = em.createNamedQuery(Queries.GET_SCHOOL_BY_NAME, School.class);
        query.setParameter(Parameters.SCHOOL_NAME, schoolName);
        return PersistanceHelper.isObjectExist(query);
    }


    private static final class Queries{

        private static final String GET_SCHOOL_BY_NAME = "School.getSchoolByName";

    }

    private static final class Parameters{

        private static final String SCHOOL_NAME = "schoolName";


    }

}
