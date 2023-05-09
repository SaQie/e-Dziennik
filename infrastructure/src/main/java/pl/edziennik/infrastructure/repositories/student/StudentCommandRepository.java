package pl.edziennik.infrastructure.repositories.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Pesel;
import pl.edziennik.domain.role.RoleId;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.student.StudentId;

import java.util.Optional;

@RepositoryDefinition(domainClass = Student.class, idClass = StudentId.class)
public interface StudentCommandRepository {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Student s " +
            "JOIN s.parent p " +
            "WHERE s.id = :#{#studentId.id()}")
    boolean hasAlreadyAssignedParent(StudentId studentId);


    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Student s " +
            "JOIN s.personInformation pi " +
            "JOIN s.user u " +
            "WHERE pi.pesel = :pesel " +
            "AND u.role.id = :#{#roleId.id()}")
    boolean isStudentExistsByPesel(Pesel pesel, RoleId roleId);

    void delete(Student student);

    Optional<Student> findById(StudentId studentId);

    Student save(Student student);

    Student getReferenceById(StudentId studentId);
}
