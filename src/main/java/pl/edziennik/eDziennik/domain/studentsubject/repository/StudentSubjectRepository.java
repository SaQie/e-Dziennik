package pl.edziennik.eDziennik.domain.studentsubject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.studentsubject.domain.StudentSubject;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Long> {

    boolean existsByStudentIdAndSubjectId(Long idStudent, Long idSubject);

    Optional<StudentSubject> findByStudentIdAndSubjectId(Long idStudent, Long idSubject);

    @Query("SELECT ss from StudentSubject ss JOIN FETCH ss.student " +
            "JOIN FETCH ss.subject " +
            "WHERE ss.student.id =:idStudent")
    List<StudentSubject> findStudentSubjectsByStudentId(Long idStudent);

}
