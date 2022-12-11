package pl.edziennik.eDziennik.server.studensubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;
import pl.edziennik.eDziennik.server.studensubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.services.validator.StudentSubjectValidators;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentSubjectValidatorService extends ServiceValidator<StudentSubjectValidators, StudentSubjectRequestDto> {

    private final ResourceCreator resourceCreator;

    private final StudentSubjectDao dao;

    public StudentSubject checkStudentSubjectExist(Long idStudent, Long idSubject){
        Optional<StudentSubject> subjectStudent = dao.findSubjectStudent(idStudent, idSubject);
        if (subjectStudent.isPresent()){
            return subjectStudent.get();
        }
        Student student = dao.get(Student.class, idStudent);
        String studentName = student.getPersonInformation().getFirstName() + " " + student.getPersonInformation().getLastName();
        String subjectName = dao.get(Subject.class, idSubject).getName();
        String exceptionMessage = resourceCreator.of("student.subject.not.exist", studentName, subjectName);
        throw new EntityNotFoundException(exceptionMessage);
    }

    protected StudentSubject validateDtoAndMapToEntity(StudentSubjectRequestDto dto){
        super.validate(dto);
        StudentSubject studentSubject = new StudentSubject();
        Subject subject = dao.get(Subject.class,dto.getIdSubject());
        Student student = dao.get(Student.class,dto.getIdStudent());
        studentSubject.setSubject(subject);
        studentSubject.setStudent(student);
        return studentSubject;
    }




}
