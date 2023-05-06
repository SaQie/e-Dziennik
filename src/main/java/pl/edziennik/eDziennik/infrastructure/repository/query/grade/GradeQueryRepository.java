package pl.edziennik.eDziennik.infrastructure.repository.query.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;

@Repository
public interface GradeQueryRepository extends JpaRepository<Grade, GradeId> {
}
