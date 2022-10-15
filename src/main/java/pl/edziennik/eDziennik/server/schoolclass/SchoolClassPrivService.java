package pl.edziennik.eDziennik.server.schoolclass;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.school.SchoolDao;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class SchoolClassPrivService {

    private final SchoolDao dao;

    protected void checkSupervisingTeacherExist(Long supervisingTeacherId, SchoolClass schoolClass) {
        if (supervisingTeacherId != null){
            dao.find(Teacher.class,supervisingTeacherId).ifPresentOrElse(schoolClass::setTeacher, () -> {
                throw new EntityNotFoundException("Teacher with given id " + supervisingTeacherId + " not exist");
            });
        }
    }

    public void checkSchoolExist(Long schoolId, SchoolClass schoolClass) {
        dao.find(schoolId).ifPresentOrElse(schoolClass::setSchool, () -> {
            throw new EntityNotFoundException("School with given id " + schoolId + " not exist");
        });
    }
}
