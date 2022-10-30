package pl.edziennik.eDziennik.server.studensubject.dao;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;

import java.util.List;
import java.util.Optional;

public interface StudentSubjectDao extends IBaseDao<StudentSubject> {

    List<StudentSubject> findAllStudentSubjectsForStudent(Long studentId);

    boolean isStudentSubjectAlreadyExist(Long studentId, Long subjectId);

    Optional<StudentSubject> findStudentSubjectByStudentAndSubjectIds(Long studentId, Long subjectId);



}
