package pl.edziennik.eDziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.entities.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
