package pl.edziennik.eDziennik.infrastructure.repository.query.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;
import pl.edziennik.eDziennik.domain.role.domain.wrapper.RoleId;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;

@Repository
public interface StudentQueryRepository extends JpaRepository<Student, StudentId> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Student s " +
            "JOIN s.personInformation pi " +
            "JOIN s.user u " +
            "WHERE pi.pesel = :pesel " +
            "AND u.role.id = :#{#roleId.id()}")
    boolean isStudentExistsByPesel(Pesel pesel, RoleId roleId);
}
