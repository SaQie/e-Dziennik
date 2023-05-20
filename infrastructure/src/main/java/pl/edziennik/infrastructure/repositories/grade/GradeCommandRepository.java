package pl.edziennik.infrastructure.repositories.grade;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.domain.grade.Grade;

@RepositoryDefinition(domainClass = Grade.class, idClass = GradeId.class)
public interface GradeCommandRepository {

    Grade save(Grade grade);

    Grade getReferenceById(GradeId gradeId);

    Grade getByGradeId(GradeId gradeId);

}
