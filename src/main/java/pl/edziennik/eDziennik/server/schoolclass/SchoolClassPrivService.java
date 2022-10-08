package pl.edziennik.eDziennik.server.schoolclass;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.school.SchoolRepository;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.teacher.TeacherRepository;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class SchoolClassPrivService {

    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;

    protected void checkSupervisingTeacherExist(Long supervisingTeacherId, SchoolClass schoolClass) {
        if (supervisingTeacherId != null){
            teacherRepository.findById(supervisingTeacherId).ifPresentOrElse(schoolClass::setTeacher, () -> {
                throw new EntityNotFoundException("Teacher with given id " + supervisingTeacherId + " not exist");
            });
        }
    }

    public void checkSchoolExist(Long schoolId, SchoolClass schoolClass) {
        schoolRepository.findById(schoolId).ifPresentOrElse(schoolClass::setSchool, () -> {
            throw new EntityNotFoundException("School with given id " + schoolId + " not exist");
        });
    }
}
