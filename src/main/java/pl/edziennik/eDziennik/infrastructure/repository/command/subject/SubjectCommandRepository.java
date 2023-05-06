package pl.edziennik.eDziennik.infrastructure.repository.command.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;

@Repository
public interface SubjectCommandRepository extends JpaRepository<Subject, SubjectId> {
}
