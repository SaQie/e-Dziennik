package pl.edziennik.eDziennik.server.schoolclass.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class SchoolClassPrivService {

    private final SchoolDao dao;

    protected void checkSupervisingTeacherExist(Long supervisingTeacherId, SchoolClass schoolClass) {
        if (supervisingTeacherId != null){
            dao.findWithExistCheck(Teacher.class, supervisingTeacherId, schoolClass::setTeacher);
        }
    }

    public void checkSchoolExist(Long schoolId, SchoolClass schoolClass) {
        dao.findWithExistCheck(schoolId, schoolClass::setSchool);
    }
}
