package pl.edziennik.eDziennik.domain.studentsubject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.studentsubject.domain.StudentSubject;
import pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper.StudentSubjectId;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, StudentSubjectId> {

    @Query("SELECT CASE WHEN COUNT(ss) > 0 THEN TRUE ELSE FALSE END FROM StudentSubject ss " +
            "WHERE ss.student.id = :#{#studentId.id()} " +
            "AND ss.subject.id = :#{#subjectId.id()}")
    boolean existsByStudentIdAndSubjectId(StudentId studentId, SubjectId subjectId);

    @Query("SELECT ss FROM StudentSubject ss " +
            "WHERE ss.student.id = :#{#studentId.id()} " +
            "AND ss.subject.id = :#{#subjectId.id()}")
    Optional<StudentSubject> findByStudentIdAndSubjectId(StudentId studentId, SubjectId subjectId);

    @Query("SELECT ss from StudentSubject ss " +
            "JOIN FETCH ss.student " +
            "JOIN FETCH ss.subject " +
            "WHERE ss.student.id =:#{#studentId.id()}")
    List<StudentSubject> findStudentSubjectsByStudentId(StudentId studentId);

}
