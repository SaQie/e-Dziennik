package pl.edziennik.infrastructure.repositories.teacher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Pesel;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.teacher.Teacher;

import java.util.Optional;

@RepositoryDefinition(domainClass = Teacher.class, idClass = TeacherId.class)
public interface TeacherCommandRepository {
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t " +
            "JOIN t.user u " +
            "WHERE u.email = :email")
    boolean isExistsByEmail(Email email);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t " +
            "WHERE t.personInformation.pesel = :pesel")
    boolean isExistsByPesel(Pesel pesel);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t " +
            "JOIN t.user u " +
            "WHERE u.username = :username")
    boolean isExistsByUsername(Username username);

    Teacher save(Teacher teacher);

    Optional<Teacher> findById(TeacherId teacherId);

    Teacher getReferenceById(TeacherId teacherId);

    @Query("SELECT CASE WHEN COUNT(sc) > 0 THEN TRUE ELSE FALSE END FROM SchoolClass sc " +
            "JOIN sc.teacher t " +
            "WHERE t.teacherId = :teacherId")
    boolean isAlreadySupervisingTeacher(TeacherId teacherId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t " +
            "JOIN t.school s " +
            "WHERE t.teacherId = :teacherId " +
            "AND s.schoolId = :schoolId ")
    boolean isAssignedToSchool(TeacherId teacherId, SchoolId schoolId);
}
