package pl.edziennik.infrastructure.repository.teacher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.teacher.Teacher;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Teacher.class, idClass = TeacherId.class)
public interface TeacherCommandRepository {

    Teacher save(Teacher teacher);

    Optional<Teacher> findById(TeacherId teacherId);

    Teacher getReferenceById(TeacherId teacherId);

    @Query("SELECT t FROM Teacher t where t.user.userId IN (:userIds)")
    List<Teacher> getTeachersByUserIds(List<UserId> userIds);

    void deleteAll(Iterable<Teacher> teacherIds);

    @Query("SELECT CASE WHEN COUNT(sc) > 0 THEN TRUE ELSE FALSE END FROM SchoolClass sc " +
            "JOIN sc.teacher t " +
            "WHERE t.teacherId = :teacherId")
    boolean isAlreadySupervisingTeacher(TeacherId teacherId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t " +
            "JOIN t.school s " +
            "WHERE t.teacherId = :teacherId " +
            "AND s.schoolId = :schoolId ")
    boolean isAssignedToSchool(TeacherId teacherId, SchoolId schoolId);

    Teacher getByTeacherId(TeacherId teacherId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Teacher t " +
            "JOIN t.user u " +
            "WHERE t.teacherId = :teacherId " +
            "AND u.isActive = false")
    boolean isTeacherAccountNotActive(TeacherId teacherId);
}
