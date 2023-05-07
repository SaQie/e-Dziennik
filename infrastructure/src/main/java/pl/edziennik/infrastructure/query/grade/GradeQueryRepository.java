package pl.edziennik.infrastructure.query.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.grade.GradeId;

@Repository
public interface GradeQueryRepository extends JpaRepository<Grade, GradeId> {
}
