package pl.edziennik.infrastructure.repository.grade;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.domain.grade.Grade;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Grade.class, idClass = GradeId.class)
public interface GradeCommandRepository {

    Grade save(Grade grade);

    Grade getReferenceById(GradeId gradeId);

    Grade getByGradeId(GradeId gradeId);

    Optional<Grade> findById(GradeId gradeId);

    void deleteById(GradeId gradeId);

    @Query("SELECT g FROM StudentSubject ss " +
            "JOIN ss.grades g " +
            "WHERE ss.subject = :subjectId ")
    List<Grade> getGradesBySubjectId(SubjectId subjectId);
}
