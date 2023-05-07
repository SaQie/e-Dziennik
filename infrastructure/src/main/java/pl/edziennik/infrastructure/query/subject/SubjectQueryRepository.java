package pl.edziennik.infrastructure.query.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.subject.SubjectId;

@Repository
public interface SubjectQueryRepository extends JpaRepository<Subject, SubjectId> {
}
