package pl.edziennik.infrastructure.repositories.teacher;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.teacher.Teacher;

@RepositoryDefinition(domainClass = Teacher.class, idClass = TeacherId.class)
public interface TeacherCommandRepository {
}
