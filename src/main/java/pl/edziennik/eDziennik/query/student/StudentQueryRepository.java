package pl.edziennik.eDziennik.query.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;

@Repository
public interface StudentQueryRepository extends JpaRepository<Student, StudentId> {
}
