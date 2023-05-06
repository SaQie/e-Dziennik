package pl.edziennik.eDziennik.infrastructure.repository.query.studentsubject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.studentsubject.domain.StudentSubject;
import pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper.StudentSubjectId;

@Repository
public interface StudentSubjectQueryRepository extends JpaRepository<StudentSubject, StudentSubjectId> {
}
