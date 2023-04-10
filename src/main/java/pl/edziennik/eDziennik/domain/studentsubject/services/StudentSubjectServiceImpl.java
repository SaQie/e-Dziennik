package pl.edziennik.eDziennik.domain.studentsubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.repository.StudentRepository;
import pl.edziennik.eDziennik.domain.studentsubject.domain.StudentSubject;
import pl.edziennik.eDziennik.domain.studentsubject.dto.mapper.StudentSubjectMapper;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.AllStudentsGradesInSubjectsDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.domain.studentsubject.repository.StudentSubjectRepository;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.repository.SubjectRepository;
import pl.edziennik.eDziennik.server.basics.service.BaseService;

import java.util.List;

@Service
@AllArgsConstructor
class StudentSubjectServiceImpl extends BaseService implements StudentSubjectService {

    private final StudentSubjectRepository repository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final StudentSubjectValidatorService validatorService;


    @Override
    @Transactional
    public StudentSubjectResponseDto assignStudentToSubject(StudentSubjectRequestDto dto) {
        validatorService.valid(dto);
        StudentSubject studentSubject = mapToEntity(dto.idStudent(), dto.idSubject());
        StudentSubject savedSubjectStudent = repository.save(studentSubject);
        return StudentSubjectMapper.toStudentSubjectResponseDto(savedSubjectStudent);
    }


    @Override
    public StudentGradesInSubjectDto getStudentSubjectGrades(Long idStudent, Long idSubject) {
        StudentSubject studentSubject = repository.findByStudentIdAndSubjectId(idStudent, idSubject)
                .orElseThrow(notFoundException(idStudent, Student.class));;
        return StudentSubjectMapper.toStudentSubjectRatingsDto(studentSubject);
    }

    @Override
    public AllStudentsGradesInSubjectsDto getStudentAllSubjectsGrades(Long idStudent) {
        List<StudentSubject> entities = repository.findStudentSubjectsByStudentId(idStudent);
        Student student = studentRepository.findById(idStudent)
                .orElseThrow(notFoundException(idStudent, Student.class));
        return StudentSubjectMapper.toAllStudentSubjectRatingDto(entities, student);
    }

    @Override
    public StudentSubjectsResponseDto getStudentSubjects(Long idStudent) {
        List<StudentSubject> entities = repository.findStudentSubjectsByStudentId(idStudent);
        Student student = studentRepository.findById(idStudent)
                .orElseThrow(notFoundException(idStudent, Student.class));
        return StudentSubjectMapper.toStudentSubjectsResponseDto(entities, student);
    }


    private StudentSubject mapToEntity(Long idStudent, Long idSubject) {
        Student student = studentRepository.findById(idStudent)
                .orElseThrow(notFoundException(idStudent, Student.class));
        Subject subject = subjectRepository.findById(idSubject)
                .orElseThrow(notFoundException(idSubject, Subject.class));
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setStudent(student);
        studentSubject.setSubject(subject);
        return studentSubject;
    }

}
