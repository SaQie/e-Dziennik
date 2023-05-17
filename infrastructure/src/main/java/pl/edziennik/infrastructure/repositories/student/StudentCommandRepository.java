package pl.edziennik.infrastructure.repositories.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.Pesel;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.domain.student.Student;

import java.util.Optional;

@RepositoryDefinition(domainClass = Student.class, idClass = StudentId.class)
public interface StudentCommandRepository {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Student s " +
            "JOIN s.parent p " +
            "WHERE s.studentId = :studentId")
    boolean hasAlreadyAssignedParent(StudentId studentId);


    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Student s " +
            "JOIN s.personInformation pi " +
            "JOIN s.user u " +
            "WHERE pi.pesel = :pesel " +
            "AND u.role.name = :name")
    boolean isStudentExistsByPesel(Pesel pesel, Name name);

    void delete(Student student);

    Optional<Student> findById(StudentId studentId);

    Student save(Student student);

    Student getReferenceById(StudentId studentId);


    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Student s " +
            "JOIN s.user u " +
            "WHERE u.username = :username ")
    boolean existsByUsername(Username username);


    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Student s " +
            "JOIN s.user u " +
            "WHERE u.email = :email ")
    boolean existsByEmail(Email email);
}
