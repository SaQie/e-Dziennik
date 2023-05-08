package pl.edziennik.infrastructure.query.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.student.StudentId;

@Repository
public interface StudentQueryRepository extends JpaRepository<Student, StudentId> {

}
