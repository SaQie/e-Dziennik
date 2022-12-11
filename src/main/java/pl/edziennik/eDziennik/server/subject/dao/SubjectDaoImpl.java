package pl.edziennik.eDziennik.server.subject.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.TypedQuery;


@Repository
class SubjectDaoImpl extends BaseDao<Subject> implements SubjectDao {
    @Override
    public boolean isSubjectAlreadyExist(String name, Long idSchoolClass) {
        TypedQuery<Subject> query = em.createNamedQuery(Queries.GET_SUBJECT_BY_NAME_AND_SCHOOL_CLASS, Subject.class);
        query.setParameter(Parameters.SUBJECT_NAME, name);
        query.setParameter(Parameters.ID_SCHOOL_CLASS, idSchoolClass);
        return PersistanceHelper.isObjectExist(query);
    }


    private static final class Parameters {

        private static final String SUBJECT_NAME = "subjectName";
        private static final String ID_SCHOOL_CLASS = "idSchoolClass";

    }

    private static final class Queries {

        private static final String GET_SUBJECT_BY_NAME_AND_SCHOOL_CLASS = "Subject.getSubjectByNameAndSchoolClass";

    }
}
