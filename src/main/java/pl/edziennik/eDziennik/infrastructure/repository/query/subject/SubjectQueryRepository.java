package pl.edziennik.eDziennik.infrastructure.repository.query.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;

@Repository
public interface SubjectQueryRepository extends JpaRepository<Subject, SubjectId> {
}
