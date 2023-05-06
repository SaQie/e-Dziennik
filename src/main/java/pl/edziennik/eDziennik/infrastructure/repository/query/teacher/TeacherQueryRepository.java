package pl.edziennik.eDziennik.infrastructure.repository.query.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;

@Repository
public interface TeacherQueryRepository extends JpaRepository<Teacher, TeacherId> {
}
