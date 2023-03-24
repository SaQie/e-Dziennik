package pl.edziennik.eDziennik.domain.subject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    boolean existsByNameAndSchoolClassId(String name, Long idSchoolClass);

}
