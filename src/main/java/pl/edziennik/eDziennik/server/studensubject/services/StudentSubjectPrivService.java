package pl.edziennik.eDziennik.server.studensubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.studensubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

import javax.persistence.EntityExistsException;

@Service
@AllArgsConstructor
public class StudentSubjectPrivService {

    private final StudentSubjectDao dao;

    public Student checkStudentExist(final Long idStudent){
        return dao.get(Student.class, idStudent);
    }

    public Subject checkSubjectExist(final Long idSubject){
        return dao.get(Subject.class, idSubject);
    }

    public void isStudentSubjectAlreadyExist(Long idSubject, Long idStudent) {
        if (dao.isStudentSubjectAlreadyExist(idStudent, idSubject)){
            throw new EntityExistsException("Student with id " + idStudent + " is already assigned to subject with id " + idSubject);
        }
    }

    public StudentSubject checkStudentSubjectExist(Long idSubject, Long idStudent){
        return dao.findStudentSubjectByStudentAndSubjectIds(idStudent, idSubject).orElseThrow(() -> new EntityNotFoundException("Student with assigned subject not found !"));
    }
}
