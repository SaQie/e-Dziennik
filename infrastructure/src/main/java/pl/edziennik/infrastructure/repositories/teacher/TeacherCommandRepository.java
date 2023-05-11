package pl.edziennik.infrastructure.repositories.teacher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Pesel;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.teacher.Teacher;

@RepositoryDefinition(domainClass = Teacher.class, idClass = TeacherId.class)
public interface TeacherCommandRepository {
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t " +
            "JOIN t.user u " +
            "WHERE u.email = :email")
    boolean existsByEmail(Email email);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t " +
            "WHERE t.personInformation.pesel = :pesel")
    boolean existsByPesel(Pesel pesel);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t " +
            "JOIN t.user u " +
            "WHERE u.username = :username")
    boolean existsByUsername(Username username);

    Teacher save(Teacher teacher);
}
