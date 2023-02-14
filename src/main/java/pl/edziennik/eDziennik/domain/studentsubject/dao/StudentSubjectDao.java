package pl.edziennik.eDziennik.domain.studentsubject.dao;

import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;
import pl.edziennik.eDziennik.domain.studentsubject.domain.StudentSubject;

import java.util.List;
import java.util.Optional;

public interface StudentSubjectDao extends IBaseDao<StudentSubject> {

    List<StudentSubject> findAllStudentSubjectsForStudent(Long studentId);

    boolean isStudentSubjectAlreadyExist(Long studentId, Long subjectId);

    Optional<StudentSubject> findSubjectStudent(Long studentId, Long subjectId);



}
