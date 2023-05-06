package pl.edziennik.eDziennik.infrastructure.repository.command.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;

@Repository
public interface TeacherCommandRepository extends JpaRepository<Teacher, TeacherId> {
}
