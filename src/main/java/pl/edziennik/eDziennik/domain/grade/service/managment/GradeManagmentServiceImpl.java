package pl.edziennik.eDziennik.domain.grade.service.managment;

import lombok.AllArgsConstructor;
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
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.domain.studentsubject.repository.StudentSubjectRepository;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.repository.TeacherRepository;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

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
    public StudentGradesInSubjectDto assignGradeToStudentSubject(final StudentSubjectSeparateId studentSubjectId, final GradeRequestApiDto dto) {
        StudentSubject studentSubject = studentSubjectRepository.findByStudentIdAndSubjectId(studentSubjectId.idStudent().id(), studentSubjectId.idSubject().id())
                .orElseThrow(notFoundException(studentSubjectId.idStudent().id(), Student.class));
        Grade grade = insertNewGrade(dto, studentSubject);
        studentSubject.addGrade(grade);
        StudentSubject studentSubjectAfterSave = studentSubjectRepository.save(studentSubject);
        return StudentSubjectMapper.toStudentSubjectRatingsDto(studentSubjectAfterSave);
    }

    @Override
    public void deleteGradeFromStudentSubject(final StudentSubjectSeparateId studentSubjectId, GradeId idGrade) {
        // TODO -> Sprawdzac czy ocena zgadza sie z tym studentem i przedmiotem
        StudentSubject studentSubject = studentSubjectRepository.findByStudentIdAndSubjectId(
                studentSubjectId.idStudent().id(), studentSubjectId.idSubject().id())
                .orElseThrow(notFoundException(studentSubjectId.idStudent().id(), Student.class));
        validatorService.checkGradeExistInStudentSubject(idGrade, studentSubject.getStudentSubjectId());
        gradeService.deleteGradeById(idGrade);
    }

    @Override
    public StudentGradesInSubjectDto updateStudentSubjectGrade(final StudentSubjectSeparateId studentSubjectId, GradeId gradeId, GradeRequestApiDto requestApiDto) {
        StudentSubject studentSubject = studentSubjectRepository.findByStudentIdAndSubjectId(studentSubjectId.idStudent().id(), studentSubjectId.idSubject().id())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student " + studentSubjectId.idStudent().id() + " not assigned to subject " + studentSubjectId.idSubject().id()));
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
        Teacher teacher = teacherRepository.getByUserUsername(dto.getTeacherName());
        Long idGrade = gradeService.addNewGrade(dto).id();
        Grade grade = repository.findById(GradeId.wrap(idGrade))
                .orElseThrow(notFoundException(idGrade, Grade.class));
        grade.setStudentSubject(studentSubject);
        grade.setTeacher(teacher);
        return grade;
    }


}
