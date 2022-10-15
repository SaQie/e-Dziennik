package pl.edziennik.eDziennik.server.teacher;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class TeacherDaoImpl extends BaseDao<Teacher> implements TeacherDao {
    @Override
    public Teacher findByUsername(String username) {
        try {
            TypedQuery<Teacher> query = em.createNamedQuery("Teacher.findByUsername", Teacher.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    // DataAccessUtils -> Jest taka klasa springowa do pomocy w dao
    // Przemysl czy wykorzystac JpaResultHelper ? (getSignleResultOrNull)
}
