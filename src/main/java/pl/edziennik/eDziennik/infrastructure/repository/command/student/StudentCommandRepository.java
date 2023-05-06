package pl.edziennik.eDziennik.infrastructure.repository.command.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;

@Repository
public interface StudentCommandRepository extends JpaRepository<Student, StudentId> {
}
