package pl.edziennik.eDziennik.server.subjectclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.subjectclass.domain.SubjectClassLink;

@Repository
public interface SubjectClassRepository extends JpaRepository<SubjectClassLink,Long> {

}
