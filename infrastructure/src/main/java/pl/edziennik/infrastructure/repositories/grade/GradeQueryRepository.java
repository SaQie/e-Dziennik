package pl.edziennik.infrastructure.repositories.grade;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.grade.GradeId;

@RepositoryDefinition(domainClass = Grade.class, idClass = GradeId.class)
public interface GradeQueryRepository {
}
