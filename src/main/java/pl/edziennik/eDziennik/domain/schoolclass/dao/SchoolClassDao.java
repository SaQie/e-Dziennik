package pl.edziennik.eDziennik.domain.schoolclass.dao;

import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;

import java.util.List;

public interface SchoolClassDao extends IBaseDao<SchoolClass> {

    boolean isSchoolClassAlreadyExist(String className, Long idSchool);

    boolean isTeacherBelongsToSchool(Long idTeacher, Long idSchool);

    boolean isTeacherAlreadySupervisingTeacher(Long idTeacher);

    String findSchoolClassNameBySupervisingTeacher(Long idSupervisingTeacher);

    List<SchoolClass> findSchoolClassesBySchoolId(Long schoolId);
}
