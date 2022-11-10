package pl.edziennik.eDziennik.server.student.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.TypedQuery;

@Repository
class StudentDaoImpl extends BaseDao<Student> implements StudentDao {
    @Override
    public Student getByUsername(String username) {
        TypedQuery<Student> query = em.createNamedQuery(Queries.GET_STUDENT_BY_USERNAME, Student.class);
        query.setParameter(Parameters.USERNAME, username);
        return (Student) PersistanceHelper.getSingleResultOrNull(query);
    }

    private static final class Parameters {

        private static final String USERNAME = "username";

    }

    private static final class Queries {

        private static final String GET_STUDENT_BY_USERNAME = "Student.getStudentByUsername";

    }


}
