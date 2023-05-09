package pl.edziennik.infrastructure.repositories.teacher;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.teacher.TeacherId;

@RepositoryDefinition(domainClass = Teacher.class, idClass = TeacherId.class)
public interface TeacherCommandRepository {
}
