package pl.edziennik.eDziennik.server.studensubject.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
class StudentSubjectDaoImpl extends BaseDao<StudentSubject> implements StudentSubjectDao {

    @Override
    public List<StudentSubject> findAllStudentSubjectsForStudent(Long studentId) {
        TypedQuery<StudentSubject> namedQuery = em.createNamedQuery(Queries.FIND_ALL_SUBJECT_STUDENT_FOR_STUDENT, StudentSubject.class);
        namedQuery.setParameter(Parameters.ID_STUDENT, studentId);
        return namedQuery.getResultList();
    }

    @Override
    public boolean isStudentSubjectAlreadyExist(Long studentId, Long subjectId) {
        Query query = em.createNamedQuery(Queries.FIND_SUBJECT_STUDENT);
        query.setParameter(Parameters.ID_STUDENT, studentId);
        query.setParameter(Parameters.ID_SUBJECT, subjectId);
        return PersistanceHelper.isObjectExist(query);
    }

    @Override
    public Optional<StudentSubject> findSubjectStudent(Long studentId, Long subjectId) {
        TypedQuery<StudentSubject> query = em.createNamedQuery(Queries.FIND_SUBJECT_STUDENT, StudentSubject.class);
        query.setParameter(Parameters.ID_STUDENT, studentId);
        query.setParameter(Parameters.ID_SUBJECT, subjectId);
        return PersistanceHelper.getSingleResultAsOptional(query);
    }


    private static final class Queries{

        private static final String FIND_SUBJECT_STUDENT = "StudentSubject.findSubjectStudent";
        private static final String FIND_ALL_SUBJECT_STUDENT_FOR_STUDENT = "StudentSubject.findAllStudentRatingsForStudent";

    }

    private static final class Parameters{

        private static final String ID_STUDENT = "idStudent";
        private static final String ID_SUBJECT = "idSubject";

    }
}
