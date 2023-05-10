package pl.edziennik.infrastructure.repositories.subject;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.domain.subject.Subject;

@RepositoryDefinition(domainClass = Subject.class, idClass = SubjectId.class)
public interface SubjectCommandRepository {
}
