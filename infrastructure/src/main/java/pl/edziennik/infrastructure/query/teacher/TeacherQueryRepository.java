package pl.edziennik.infrastructure.query.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.teacher.TeacherId;

@Repository
public interface TeacherQueryRepository extends JpaRepository<Teacher, TeacherId> {
}
