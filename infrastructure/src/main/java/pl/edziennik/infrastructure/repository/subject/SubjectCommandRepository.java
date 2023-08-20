package pl.edziennik.infrastructure.repository.subject;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.subject.Subject;

import java.util.Optional;

@RepositoryDefinition(domainClass = Subject.class, idClass = SubjectId.class)
public interface SubjectCommandRepository {
    @Query("SELECT CASE WHEN COUNT(t) = 1 THEN TRUE ELSE FALSE END " +
            "FROM Teacher t " +
            "JOIN t.school s " +
            "JOIN SchoolClass sc ON sc.school = s " +
            "WHERE t.teacherId = :teacherId AND sc.schoolClassId = :schoolClassId")
    boolean isTeacherFromTheSameSchool(SchoolClassId schoolClassId, TeacherId teacherId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM SchoolClass sc " +
            "JOIN sc.subjects s " +
            "WHERE sc.schoolClassId = :schoolClassId " +
            "AND s.name = :name ")
    boolean existsByName(Name name, SchoolClassId schoolClassId);

    Subject save(Subject subject);

    Optional<Subject> findById(SubjectId subjectId);

    Subject getBySubjectId(SubjectId subjectId);

    @Query("SELECT CASE WHEN COUNT(s) = 1 THEN TRUE ELSE FALSE END " +
            "FROM Student s " +
            "JOIN s.schoolClass sc " +
            "JOIN Subject su ON su.schoolClass = sc " +
            "WHERE su.subjectId = :subjectId " +
            "AND s.studentId = :studentId")
    boolean isStudentFromTheSameSchoolClass(StudentId studentId, SubjectId subjectId);

    Subject getReferenceById(SubjectId subjectId);

    @Query("SELECT CASE WHEN COUNT(t) = 1 THEN TRUE ELSE FALSE END " +
            "FROM Subject s " +
            "JOIN s.teacher t " +
            "WHERE s.subjectId = :subjectId " +
            "AND t.teacherId = :teacherId ")
    boolean isTeacherFromProvidedSubject(TeacherId teacherId, SubjectId subjectId);
}
