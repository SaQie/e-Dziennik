package pl.edziennik.eDziennik.server.studensubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.grade.domain.Grade;
import pl.edziennik.eDziennik.server.studensubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.mapper.StudentSubjectMapper;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectGradeRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.AllStudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import java.util.List;

@Service
@AllArgsConstructor
class StudentSubjectServiceImpl implements StudentSubjectService {

    private final StudentSubjectDao dao;
    private final TeacherDao teacherDao;
    private final StudentSubjectPrivService privService;

    @Override
    @Transactional
    public void assignStudentToSubject(StudentSubjectRequestDto dto, Long idStudent) {
        privService.isStudentSubjectAlreadyExist(dto.getSubject(), idStudent);
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setSubject(privService.checkSubjectExist(dto.getSubject()));
        studentSubject.setStudent(privService.checkStudentExist(idStudent));
        dao.saveOrUpdate(studentSubject);
    }

    @Override
    @Transactional
    public void assignGradeToStudentSubject(Long idStudent, Long idSubject, StudentSubjectGradeRequestDto dto, String teacherName) {
        StudentSubject studentSubject = privService.checkStudentSubjectExist(idSubject, idStudent);
        Grade grade = dao.find(Grade.class, dto.getGrade()).get();
        Teacher teacher = teacherDao.getByUsername(teacherName);
        studentSubject.addRating(grade);
        grade.setStudentSubject(studentSubject);
        grade.setTeacher(teacher);
        dao.saveOrUpdate(studentSubject);
    }

    @Override
    public StudentSubjectGradesResponseDto getStudentSubjectRatings(Long idStudent, Long idSubject) {
        StudentSubject studentSubject = privService.checkStudentSubjectExist(idSubject, idStudent);
        return StudentSubjectMapper.toStudentSubjectRatingsDto(studentSubject);
    }

    @Override
    public AllStudentSubjectGradesResponseDto getStudentAllSubjectsRatings(Long idStudent) {
        List<StudentSubject> entities = dao.findAllStudentSubjectsForStudent(idStudent);
        return StudentSubjectMapper.toAllStudentSubjectRatingDto(entities);
    }

    @Override
    public StudentSubjectsResponseDto getStudentSubjects(Long idStudent) {
        List<StudentSubject> entities = dao.findAllStudentSubjectsForStudent(idStudent);
        return StudentSubjectMapper.toStudentSubjectsResponseDto(entities);
    }
}
