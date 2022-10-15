package pl.edziennik.eDziennik.server.student;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.school.SchoolDao;
import pl.edziennik.eDziennik.server.schoolclass.SchoolClassRepository;
import pl.edziennik.eDziennik.server.student.domain.Student;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class StudentPrivService {

    private final SchoolDao dao;
    private final SchoolClassRepository schoolClassRepository;

    protected void checkSchoolExist(Long idSchool, Student student) {
        dao.find(idSchool).ifPresentOrElse(student::setSchool, () -> {
            throw new EntityNotFoundException("School with given id " +idSchool + " not exist");
        });
    }

    protected void checkSchoolClassExist(Long idSchoolClass, Student student) {
        schoolClassRepository.findById(idSchoolClass).ifPresentOrElse(student::setSchoolClass, () -> {
            throw new EntityNotFoundException("School class with given id " + idSchoolClass + " not exist");
        });
    }
}
