package pl.edziennik.infrastructure.repositories.studentsubject;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.StudentSubjectId;
import pl.edziennik.domain.studentsubject.StudentSubject;

@RepositoryDefinition(domainClass = StudentSubject.class, idClass = StudentSubjectId.class)
public interface StudentSubjectCommandRepository {
}
