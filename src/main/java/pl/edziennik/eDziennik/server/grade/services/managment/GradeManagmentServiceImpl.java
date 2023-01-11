package pl.edziennik.eDziennik.server.grade.services.managment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.exceptions.BusinessException;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.BaseService;
import pl.edziennik.eDziennik.server.grade.domain.Grade;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.grade.services.GradeService;
import pl.edziennik.eDziennik.server.studensubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.mapper.StudentSubjectMapper;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.server.studensubject.services.StudentSubjectValidatorService;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import java.util.Optional;

@Service
@AllArgsConstructor
class GradeManagmentServiceImpl extends BaseService implements GradeManagmentService{

    private final TeacherDao teacherDao;
    private final GradeService gradeService;
    private final StudentSubjectDao dao;
    private final GradeManagmentValidationService validatorService;

    @Override
    @Transactional
    public StudentGradesInSubjectDto assignGradeToStudentSubject(Long idStudent, Long idSubject, GradeRequestApiDto dto) {
        StudentSubject studentSubject = basicValidator.checkStudentSubjectExist(idStudent, idSubject);
        Grade grade = insertNewGrade(dto, studentSubject);
        studentSubject.addGrade(grade);
        StudentSubject studentSubjectAfterSave = dao.saveOrUpdate(studentSubject);
        return StudentSubjectMapper.toStudentSubjectRatingsDto(studentSubjectAfterSave);
    }

    @Override
    public void deleteGradeFromStudentSubject(Long idStudent, Long idSubject, Long idGrade) {
        // TODO -> Sprawdzac czy ocena zgadza sie z tym studentem i przedmiotem
        StudentSubject studentSubject = basicValidator.checkStudentSubjectExist(idStudent, idSubject);
        validatorService.checkGradeExistInStudentSubject(idGrade, studentSubject.getId());
        gradeService.deleteGradeById(idGrade);
    }

    @Override
    public StudentGradesInSubjectDto updateStudentSubjectGrade(Long idStudent, Long idSubject, Long idGrade, GradeRequestApiDto requestApiDto) {
        StudentSubject studentSubject = dao.findSubjectStudent(idStudent, idSubject).orElseThrow(() -> new EntityNotFoundException("Student " + idStudent + " not assigned to subject " + idSubject));
        Optional<Grade> optionalGrade = dao.find(Grade.class, idGrade);
        if (optionalGrade.isPresent()){
            Grade grade = optionalGrade.get();
            grade.setGrade(Grade.GradeConst.getByRating(requestApiDto.getGrade()));
            grade.setDescription(requestApiDto.getDescription());
            grade.setWeight(requestApiDto.getWeight());
            return StudentSubjectMapper.toStudentSubjectRatingsDto(studentSubject);
        }
        throw new BusinessException("You cannot add new Grade with this method");
    }


    private Grade insertNewGrade(GradeRequestApiDto dto, StudentSubject studentSubject) {
        Teacher teacher = teacherDao.getByUsername(dto.getTeacherName());
        Long gradeId = gradeService.addNewGrade(dto).getId();
        Grade grade = dao.get(Grade.class, gradeId);
        grade.setStudentSubject(studentSubject);
        grade.setTeacher(teacher);
        return grade;
    }



}
