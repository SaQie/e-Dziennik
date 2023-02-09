package pl.edziennik.eDziennik.server.schoolclass.dao;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;

import java.util.List;
import java.util.Optional;

public interface SchoolClassDao extends IBaseDao<SchoolClass> {

    boolean isSchoolClassAlreadyExist(String className, Long idSchool);

    boolean isTeacherBelongsToSchool(Long idTeacher, Long idSchool);

    boolean isTeacherAlreadySupervisingTeacher(Long idTeacher);

    String findSchoolClassNameBySupervisingTeacher(Long idSupervisingTeacher);

    List<SchoolClass> findSchoolClassesBySchoolId(Long schoolId);
}
