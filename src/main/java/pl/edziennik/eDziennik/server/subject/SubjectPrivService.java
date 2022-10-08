package pl.edziennik.eDziennik.server.subject;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.schoolclass.SchoolClassRepository;
import pl.edziennik.eDziennik.server.subjectclass.domain.SubjectClassLink;
import pl.edziennik.eDziennik.server.teacher.TeacherRepository;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class SubjectPrivService {

    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;

    protected void checkTeacherExist(Long teacherId, Subject subject) {
        teacherRepository.findById(teacherId).ifPresentOrElse(subject::setTeacher, () -> {
            throw new EntityNotFoundException("Teacher with given id " + teacherId + " not exist");
        });
    }

    protected void checkSchoolClassExist(Long schoolClassId, SubjectClassLink subjectClassLink) {
        schoolClassRepository.findById(schoolClassId).ifPresentOrElse(subjectClassLink::setSchoolClass, () -> {
            throw new EntityNotFoundException("School class with given id " + schoolClassId + " not exist");
        });
    }
}
