package pl.edziennik.eDziennik.domain.subject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, SubjectId> {

    boolean existsByNameAndSchoolClassId(String name, Long schoolClassId);

}
