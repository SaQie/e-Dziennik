package pl.edziennik.infrastructure.query.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.personinfromation.Pesel;
import pl.edziennik.domain.role.RoleId;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.student.StudentId;

@Repository
public interface StudentQueryRepository extends JpaRepository<Student, StudentId> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Student s " +
            "JOIN s.personInformation pi " +
            "JOIN s.user u " +
            "WHERE pi.pesel = :pesel " +
            "AND u.role.id = :#{#roleId.id()}")
    boolean isStudentExistsByPesel(Pesel pesel, RoleId roleId);
}
