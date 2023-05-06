package pl.edziennik.eDziennik.domain.grade.service.managment;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.grade.repository.GradeRepository;
import pl.edziennik.eDziennik.domain.grade.service.GradeService;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.studentsubject.domain.StudentSubject;
import pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper.StudentSubjectSeparateId;
import pl.edziennik.eDziennik.domain.studentsubject.dto.mapper.StudentSubjectMapper;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.studentsubject.repository.StudentSubjectRepository;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.repository.TeacherRepository;
import pl.edziennik.eDziennik.server.basic.service.BaseService;
import pl.edziennik.eDziennik.server.exception.BusinessException;
import pl.edziennik.eDziennik.server.exception.EntityNotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
class GradeManagmentServiceImpl extends BaseService implements GradeManagmentService {

    private final TeacherRepository teacherRepository;
    private final GradeService gradeService;
    private final GradeRepository repository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final GradeManagmentValidationService validatorService;

    @Override
    @Transactional
    public StudentGradesInSubjectResponseApiDto assignGradeToStudentSubject(final StudentSubjectSeparateId studentSubjectId, final GradeRequestApiDto dto) {
        StudentSubject studentSubject = studentSubjectRepository.findByStudentIdAndSubjectId(studentSubjectId.studentId(), studentSubjectId.subjectId())
                .orElseThrow(notFoundException(studentSubjectId.studentId(), Student.class));
        Grade grade = insertNewGrade(dto, studentSubject);
        studentSubject.addGrade(grade);
        StudentSubject studentSubjectAfterSave = studentSubjectRepository.save(studentSubject);
        return StudentSubjectMapper.toStudentSubjectRatingsDto(studentSubjectAfterSave);
    }

    @Override
    public void deleteGradeFromStudentSubject(final StudentSubjectSeparateId studentSubjectId, GradeId idGrade) {
        // TODO -> Sprawdzac czy ocena zgadza sie z tym studentem i przedmiotem
        StudentSubject studentSubject = studentSubjectRepository.findByStudentIdAndSubjectId(
                studentSubjectId.studentId(), studentSubjectId.subjectId())
                .orElseThrow(notFoundException(studentSubjectId.studentId(), Student.class));
        validatorService.checkGradeExistInStudentSubject(idGrade, studentSubject.getStudentSubjectId());
        gradeService.deleteGradeById(idGrade);
    }

    @Override
    public StudentGradesInSubjectResponseApiDto updateStudentSubjectGrade(final StudentSubjectSeparateId studentSubjectId, GradeId gradeId, GradeRequestApiDto requestApiDto) {
        StudentSubject studentSubject = studentSubjectRepository.findByStudentIdAndSubjectId(studentSubjectId.studentId(), studentSubjectId.subjectId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student " + studentSubjectId.studentId().id() + " not assigned to subject " + studentSubjectId.subjectId().id()));
        Optional<Grade> optionalGrade = repository.findById(gradeId);
        if (optionalGrade.isPresent()) {
            Grade grade = optionalGrade.get();
            grade.setGrade(Grade.GradeConst.getByRating(requestApiDto.getGrade()));
            grade.setDescription(requestApiDto.getDescription());
            grade.setWeight(requestApiDto.getWeight());
            return StudentSubjectMapper.toStudentSubjectRatingsDto(studentSubject);
        }
        throw new BusinessException("You cannot add new Grade with this method");
    }


    private Grade insertNewGrade(GradeRequestApiDto dto, StudentSubject studentSubject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = teacherRepository.getByUserUsername(authentication.getName());
        GradeId gradeId = gradeService.addNewGrade(dto).gradeId();
        Grade grade = repository.findById(gradeId)
                .orElseThrow(notFoundException(gradeId, Grade.class));
        grade.setStudentSubject(studentSubject);
        grade.setTeacher(teacher);
        return grade;
    }


}
