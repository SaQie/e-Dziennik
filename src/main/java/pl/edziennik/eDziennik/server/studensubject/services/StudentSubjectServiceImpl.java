package pl.edziennik.eDziennik.server.studensubject.services;

import liquibase.pro.packaged.id;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.basics.BaseService;
import pl.edziennik.eDziennik.server.studensubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.mapper.StudentSubjectMapper;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.AllStudentsGradesInSubjectsDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

import java.util.List;

@Service
@AllArgsConstructor
class StudentSubjectServiceImpl extends BaseService implements StudentSubjectService {

    private final StudentSubjectDao dao;
    private final StudentSubjectValidatorService validatorService;


    @Override
    @Transactional
    public StudentSubjectResponseDto assignStudentToSubject(StudentSubjectRequestDto dto) {
        validatorService.valid(dto);
        StudentSubject studentSubject = mapToEntity(dto.getIdStudent(), dto.getIdSubject());
        StudentSubject savedSubjectStudent = dao.saveOrUpdate(studentSubject);
        return StudentSubjectMapper.toStudentSubjectResponseDto(savedSubjectStudent);
    }


    @Override
    public StudentGradesInSubjectDto getStudentSubjectGrades(Long idStudent, Long idSubject) {
        StudentSubject studentSubject = validatorService.basicValidator.checkStudentSubjectExist(idStudent, idSubject);
        return StudentSubjectMapper.toStudentSubjectRatingsDto(studentSubject);
    }

    @Override
    public AllStudentsGradesInSubjectsDto getStudentAllSubjectsGrades(Long idStudent) {
        List<StudentSubject> entities = dao.findAllStudentSubjectsForStudent(idStudent);
        Student student = dao.get(Student.class, idStudent);
        return StudentSubjectMapper.toAllStudentSubjectRatingDto(entities, student);
    }

    @Override
    public StudentSubjectsResponseDto getStudentSubjects(Long idStudent) {
        List<StudentSubject> entities = dao.findAllStudentSubjectsForStudent(idStudent);
        Student student = dao.get(Student.class, idStudent);
        return StudentSubjectMapper.toStudentSubjectsResponseDto(entities, student);
    }


    private StudentSubject mapToEntity(Long idStudent, Long idSubject) {
        Student student = dao.get(Student.class, idStudent);
        Subject subject = dao.get(Subject.class, idSubject);
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setStudent(student);
        studentSubject.setSubject(subject);
        return studentSubject;
    }

}
