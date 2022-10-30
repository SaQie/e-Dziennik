package pl.edziennik.eDziennik.server.teacher.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.TypedQuery;

@Repository
class TeacherDaoImpl extends BaseDao<Teacher> implements TeacherDao {
    @Override
    public Teacher findByUsername(String username) {
        TypedQuery<Teacher> query = em.createNamedQuery(Queries.GET_TEACHER_BY_USERNAME, Teacher.class);
        query.setParameter(Parameters.USERNAME, username);
        return (Teacher) PersistanceHelper.getSingleResultOrNull(query);
    }


    private static final class Parameters{

        private static final String USERNAME = "username";

    }

    private static final class Queries{

        private static final String GET_TEACHER_BY_USERNAME = "Teacher.getByUsername";

    }

}
