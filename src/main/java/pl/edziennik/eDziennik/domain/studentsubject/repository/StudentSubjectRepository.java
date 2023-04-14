package pl.edziennik.eDziennik.domain.studentsubject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.studentsubject.domain.StudentSubject;
import pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper.StudentSubjectId;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, StudentSubjectId> {

    boolean existsByStudentIdAndSubjectId(Long idStudent, Long subjectId);

    Optional<StudentSubject> findByStudentIdAndSubjectId(Long studentId, Long subjectId);

    @Query("SELECT ss from StudentSubject ss " +
            "JOIN FETCH ss.student " +
            "JOIN FETCH ss.subject " +
            "WHERE ss.student.id =:studentId")
    List<StudentSubject> findStudentSubjectsByStudentId(Long studentId);

}
