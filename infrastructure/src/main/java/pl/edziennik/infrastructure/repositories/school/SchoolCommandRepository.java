package pl.edziennik.infrastructure.repositories.school;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.school.SchoolId;

@RepositoryDefinition(domainClass = School.class, idClass = SchoolId.class)
public interface SchoolCommandRepository {
    School getReferenceById(SchoolId schoolId);
}
