package pl.edziennik.eDziennik.server.studensubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.grade.dao.GradeDao;
import pl.edziennik.eDziennik.server.grade.domain.Grade;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.grade.services.GradeService;
import pl.edziennik.eDziennik.server.studensubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.mapper.StudentSubjectMapper;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.AllStudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class StudentSubjectServiceImpl implements StudentSubjectService {

    private final StudentSubjectDao dao;
    private final TeacherDao teacherDao;
    private final GradeDao gradeDao;
    private final StudentSubjectPrivService privService;
    private final GradeService gradeService;

    @Override
    @Transactional
    public StudentSubjectResponseDto assignStudentToSubject(StudentSubjectRequestDto dto, Long idStudent) {
        privService.isStudentSubjectAlreadyExist(dto.getSubject(), idStudent);
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setSubject(privService.checkSubjectExist(dto.getSubject()));
        studentSubject.setStudent(privService.checkStudentExist(idStudent));
        StudentSubject savedSubjectStudent = dao.saveOrUpdate(studentSubject);
        return StudentSubjectMapper.toStudentSubjectResponseDto(savedSubjectStudent);
    }



    @Override
    public StudentSubjectGradesResponseDto getStudentSubjectGrades(Long idStudent, Long idSubject) {
        StudentSubject studentSubject = privService.checkStudentSubjectExist(idSubject, idStudent);
        return StudentSubjectMapper.toStudentSubjectRatingsDto(studentSubject);
    }

    @Override
    public AllStudentSubjectGradesResponseDto getStudentAllSubjectsGrades(Long idStudent) {
        List<StudentSubject> entities = dao.findAllStudentSubjectsForStudent(idStudent);
        return StudentSubjectMapper.toAllStudentSubjectRatingDto(entities);
    }

    @Override
    public StudentSubjectsResponseDto getStudentSubjects(Long idStudent) {
        List<StudentSubject> entities = dao.findAllStudentSubjectsForStudent(idStudent);
        return StudentSubjectMapper.toStudentSubjectsResponseDto(entities);
    }
}
