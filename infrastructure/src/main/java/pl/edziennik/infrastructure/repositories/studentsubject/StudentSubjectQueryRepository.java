package pl.edziennik.infrastructure.repositories.studentsubject;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.studentsubject.StudentSubjectId;

@RepositoryDefinition(domainClass = StudentSubject.class, idClass = StudentSubjectId.class)
public interface StudentSubjectQueryRepository {
}
