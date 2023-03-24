package pl.edziennik.eDziennik.domain.grade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.grade.domain.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
}
