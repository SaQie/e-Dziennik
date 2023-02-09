package pl.edziennik.eDziennik.server.schoolclass.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
class SchoolClassDaoImpl extends BaseDao<SchoolClass> implements SchoolClassDao {

    @Override
    public boolean isSchoolClassAlreadyExist(String className, Long idSchool) {
        TypedQuery<SchoolClass> query = em.createNamedQuery(Queries.FIND_SCHOOL_CLASS_BY_CLASS_NAME_AND_SCHOOL, SchoolClass.class);
        query.setParameter(Parameters.CLASS_NAME, className);
        query.setParameter(Parameters.ID_SCHOOL, idSchool);
        return PersistanceHelper.isObjectExist(query);
    }

    @Override
    public boolean isTeacherBelongsToSchool(Long idTeacher, Long idSchool) {
        TypedQuery<Teacher> query = em.createNamedQuery(Queries.IS_TEACHER_BELONGS_TO_SCHOOL, Teacher.class);
        query.setParameter(Parameters.ID_TEACHER, idTeacher);
        query.setParameter(Parameters.ID_SCHOOL, idSchool);
        return PersistanceHelper.isObjectExist(query);
    }

    @Override
    public boolean isTeacherAlreadySupervisingTeacher(Long idTeacher) {
        TypedQuery<Teacher> query = em.createNamedQuery(Queries.FIND_SUPERVISING_TEACHER_SCHOOL_CLASS_BY_ID, Teacher.class);
        query.setParameter(Parameters.ID_TEACHER, idTeacher);
        return PersistanceHelper.isObjectExist(query);
    }

    @Override
    public String findSchoolClassNameBySupervisingTeacher(Long idSupervisingTeacher){
        TypedQuery<String> query = em.createNamedQuery(Queries.FIND_SCHOOL_CLASS_NAME_BY_SUPERVISING_TEACHER, String.class);
        query.setParameter(Parameters.ID_TEACHER, idSupervisingTeacher);
        return query.getSingleResult();
    }

    @Override
    public List<SchoolClass> findSchoolClassesBySchoolId(Long schoolId) {
        TypedQuery<SchoolClass> query = em.createNamedQuery(Queries.FIND_SCHOOL_CLASSES_BY_SCHOOL_ID, SchoolClass.class);
        query.setParameter(Parameters.ID_SCHOOL, schoolId);
        return query.getResultList();
    }

    private static final class Queries{

        private static final String FIND_SCHOOL_CLASS_BY_CLASS_NAME_AND_SCHOOL = "SchoolClass.getSchoolClassByClassNameAndIdSchool";
        private static final String FIND_SUPERVISING_TEACHER_SCHOOL_CLASS_BY_ID = "SchoolClass.getSupervisingTeacherById";
        private static final String FIND_SCHOOL_CLASS_NAME_BY_SUPERVISING_TEACHER = "SchoolClass.getSchoolClassBySupervisingTeacher";
        private static final String FIND_SCHOOL_CLASSES_BY_SCHOOL_ID = "SchoolClass.findSchoolClassesBySchoolId";
        private static final String IS_TEACHER_BELONGS_TO_SCHOOL = "SchoolClass.isTeacherBelongsToSchool";

    }

    private static final class Parameters{

        private static final String CLASS_NAME = "className";
        private static final String ID_TEACHER = "idTeacher";
        private static final String ID_SCHOOL = "idSchool";

    }



}
