package pl.edziennik.infrastructure.repository.studentsubject;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.StudentSubjectId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.studentsubject.StudentSubject;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = StudentSubject.class, idClass = StudentSubjectId.class)
public interface StudentSubjectCommandRepository {

    @Query("SELECT CASE WHEN COUNT(ss) = 1 THEN TRUE ELSE FALSE END " +
            "FROM StudentSubject ss " +
            "JOIN ss.subject su " +
            "JOIN ss.student st " +
            "WHERE su.subjectId = :subjectId " +
            "AND st.studentId = :studentId")
    boolean isStudentAlreadyAssignedToSubject(StudentId studentId, SubjectId subjectId);

    StudentSubject save(StudentSubject studentSubject);

    Optional<StudentSubject> findById(StudentSubjectId studentSubjectId);

    @Query("SELECT g FROM StudentSubject ss " +
            "JOIN ss.grades g " +
            "WHERE ss.studentSubjectId = :studentSubjectId ")
    List<Grade> getStudentSubjectGrades(StudentSubjectId studentSubjectId);


    StudentSubject getReferenceByStudentStudentIdAndSubjectSubjectId(StudentId studentId, SubjectId subjectId);

    StudentSubject getByStudentStudentIdAndSubjectSubjectId(StudentId studentId, SubjectId subjectId);

    @Query("DELETE FROM StudentSubject ss " +
            "WHERE ss.student.studentId = :studentId " +
            "AND ss.subject.subjectId = :subjectId ")
    @Modifying
    void deleteByStudentIdAndSubjectId(StudentId studentId, SubjectId subjectId);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END " +
            "FROM StudentSubject ss " +
            "JOIN ss.grades g " +
            "WHERE ss.student.studentId = :studentId " +
            "AND ss.subject.subjectId = :subjectId ")
    boolean existsGradesAssignedToStudentSubject(StudentId studentId, SubjectId subjectId);
}
