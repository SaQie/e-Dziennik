package pl.edziennik.infrastructure.repositories.student;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.student.StudentId;

@RepositoryDefinition(domainClass = Student.class, idClass = StudentId.class)
public interface StudentQueryRepository {

}
