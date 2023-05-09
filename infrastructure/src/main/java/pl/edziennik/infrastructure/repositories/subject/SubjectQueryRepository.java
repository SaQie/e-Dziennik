package pl.edziennik.infrastructure.repositories.subject;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.subject.SubjectId;

@RepositoryDefinition(domainClass = Subject.class, idClass = SubjectId.class)
public interface SubjectQueryRepository {
}
