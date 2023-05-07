package pl.edziennik.infrastructure.command.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.grade.GradeId;

@Repository
public interface GradeCommandRepository extends JpaRepository<Grade, GradeId> {
}
