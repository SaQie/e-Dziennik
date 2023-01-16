package pl.edziennik.eDziennik.server.teacher.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
class TeacherDaoImpl extends BaseDao<Teacher> implements TeacherDao {
    @Override
    public Teacher getByUsername(String username) {
        TypedQuery<Teacher> query = em.createNamedQuery(Queries.GET_TEACHER_BY_USERNAME, Teacher.class);
        query.setParameter(Parameters.USERNAME, username);
        return (Teacher) PersistanceHelper.getSingleResultOrNull(query);
    }

    @Override
    public boolean isTeacherExist(String username) {
        TypedQuery<Teacher> query = em.createNamedQuery(Queries.GET_TEACHER_BY_USERNAME, Teacher.class);
        query.setParameter(Parameters.USERNAME, username);
        return PersistanceHelper.isObjectExist(query);
    }

    @Override
    public boolean isTeacherExistByPesel(String pesel) {
        TypedQuery<Teacher> query = em.createNamedQuery(Queries.GET_TEACHER_BY_PESEL, Teacher.class);
        query.setParameter(Parameters.PESEL, pesel);
        return PersistanceHelper.isObjectExist(query);
    }

    @Override
    public boolean isTeacherExistByEmail(String email) {
        TypedQuery<Teacher> query = em.createNamedQuery(Queries.GET_TEACHER_BY_EMAIL, Teacher.class);
        query.setParameter(Parameters.EMAIL, email);
        return PersistanceHelper.isObjectExist(query);
    }

    private static final class Parameters{

        private static final String USERNAME = "username";
        private static final String PESEL = "pesel";
        private static final String EMAIL = "email";

    }

    private static final class Queries{

        private static final String GET_TEACHER_BY_USERNAME = "Teacher.getByUsername";
        private static final String GET_TEACHER_BY_PESEL = "Teacher.getByPesel";
        private static final String GET_TEACHER_BY_EMAIL = "Teacher.getByEmail";

    }

}
