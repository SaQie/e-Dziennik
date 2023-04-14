package pl.edziennik.eDziennik.domain.studentsubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
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
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
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
        StudentSubject studentSubject = mapToEntity(dto.studentId(), dto.subjectId());
        StudentSubject savedSubjectStudent = repository.save(studentSubject);
        return StudentSubjectMapper.toStudentSubjectResponseDto(savedSubjectStudent);
    }


    @Override
    public StudentGradesInSubjectDto getStudentSubjectGrades(final StudentId studentId, final SubjectId subjectId) {
        StudentSubject studentSubject = repository.findByStudentIdAndSubjectId(studentId.id(), subjectId.id())
                .orElseThrow(notFoundException(studentId.id(), Student.class));;
        return StudentSubjectMapper.toStudentSubjectRatingsDto(studentSubject);
    }

    @Override
    public AllStudentsGradesInSubjectsDto getStudentAllSubjectsGrades(final StudentId studentId) {
        List<StudentSubject> entities = repository.findStudentSubjectsByStudentId(studentId.id());
        Student student = studentRepository.findById(studentId)
                .orElseThrow(notFoundException(studentId.id(), Student.class));
        return StudentSubjectMapper.toAllStudentSubjectRatingDto(entities, student);
    }

    @Override
    public StudentSubjectsResponseDto getStudentSubjects(final StudentId studentId) {
        List<StudentSubject> entities = repository.findStudentSubjectsByStudentId(studentId.id());
        Student student = studentRepository.findById(studentId)
                .orElseThrow(notFoundException(studentId.id(), Student.class));
        return StudentSubjectMapper.toStudentSubjectsResponseDto(entities, student);
    }


    private StudentSubject mapToEntity(final StudentId studentId, final SubjectId subjectId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(notFoundException(studentId.id(), Student.class));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(notFoundException(subjectId.id(), Subject.class));
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setStudent(student);
        studentSubject.setSubject(subject);
        return studentSubject;
    }

}
