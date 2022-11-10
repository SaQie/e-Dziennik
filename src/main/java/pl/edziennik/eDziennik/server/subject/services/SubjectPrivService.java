package pl.edziennik.eDziennik.server.subject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class SubjectPrivService {

    private final TeacherDao dao;

    protected void checkTeacherExist(Long teacherId, Subject subject) {
        if (teacherId != null) {
            dao.findWithExecute(teacherId, subject::setTeacher);
        }
    }

}
