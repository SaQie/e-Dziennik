package pl.edziennik.eDziennik.server.student.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.student.domain.Student;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class StudentPrivService {

    private final SchoolDao dao;

    protected void checkSchoolExist(Long idSchool, Student student) {
        dao.findWithExistCheck(idSchool, student::setSchool);
    }

    protected void checkSchoolClassExist(Long idSchoolClass, Student student) {
        dao.findWithExistCheck(SchoolClass.class, idSchoolClass, student::setSchoolClass);
    }
}
