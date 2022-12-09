package pl.edziennik.eDziennik.server.studensubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.studensubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.mapper.StudentSubjectMapper;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.AllStudentsGradesInSubjectsDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectsResponseDto;

import java.util.List;

@Service
@AllArgsConstructor
class StudentSubjectServiceImpl implements StudentSubjectService {

    private final StudentSubjectDao dao;
    private final StudentSubjectValidatorService pubService;


    @Override
    @Transactional
    public StudentSubjectResponseDto assignStudentToSubject(StudentSubjectRequestDto dto) {
        StudentSubject studentSubject = pubService.validateDtoAndMapToEntity(dto);
        StudentSubject savedSubjectStudent = dao.saveOrUpdate(studentSubject);
        return StudentSubjectMapper.toStudentSubjectResponseDto(savedSubjectStudent);
    }

    @Override
    public StudentGradesInSubjectDto getStudentSubjectGrades(Long idStudent, Long idSubject) {
        StudentSubject studentSubject = pubService.checkStudentSubjectExist(idStudent, idSubject);
        return StudentSubjectMapper.toStudentSubjectRatingsDto(studentSubject);
    }

    @Override
    public AllStudentsGradesInSubjectsDto getStudentAllSubjectsGrades(Long idStudent) {
        List<StudentSubject> entities = dao.findAllStudentSubjectsForStudent(idStudent);
        return StudentSubjectMapper.toAllStudentSubjectRatingDto(entities);
    }

    @Override
    public StudentSubjectsResponseDto getStudentSubjects(Long idStudent) {
        List<StudentSubject> entities = dao.findAllStudentSubjectsForStudent(idStudent);
        return StudentSubjectMapper.toStudentSubjectsResponseDto(entities);
    }
}
