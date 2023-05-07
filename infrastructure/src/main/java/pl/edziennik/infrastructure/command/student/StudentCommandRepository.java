package pl.edziennik.infrastructure.command.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.student.StudentId;

@Repository
public interface StudentCommandRepository extends JpaRepository<Student, StudentId> {
}
