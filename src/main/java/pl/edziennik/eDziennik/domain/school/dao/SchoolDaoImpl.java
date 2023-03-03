package pl.edziennik.eDziennik.domain.school.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.server.basics.dao.BaseDao;
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

    @Override
    public boolean isSchoolWithNipExist(String nip) {
        TypedQuery<School> query = em.createNamedQuery(Queries.GET_SCHOOL_BY_NIP, School.class);
        query.setParameter(Parameters.NIP, nip);
        return PersistanceHelper.isObjectExist(query);
    }

    @Override
    public boolean isSchoolWithRegonExist(String regon) {
        TypedQuery<School> query = em.createNamedQuery(Queries.GET_SCHOOL_BY_REGON, School.class);
        query.setParameter(Parameters.REGON, regon);
        return PersistanceHelper.isObjectExist(query);
    }

    private static final class Queries{

        private static final String GET_SCHOOL_BY_NAME = "School.getSchoolByName";
        private static final String GET_SCHOOL_BY_REGON = "School.getSchoolByRegon";
        private static final String GET_SCHOOL_BY_NIP = "School.getSchoolByNip";

    }

    private static final class Parameters{

        private static final String SCHOOL_NAME = "schoolName";
        private static final String REGON = "regon";
        private static final String NIP = "nip";


    }

}