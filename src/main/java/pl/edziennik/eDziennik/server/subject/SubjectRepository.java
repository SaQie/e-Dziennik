package pl.edziennik.eDziennik.server.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
