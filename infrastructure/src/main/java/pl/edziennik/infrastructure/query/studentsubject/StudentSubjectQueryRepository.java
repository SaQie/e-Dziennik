package pl.edziennik.infrastructure.query.studentsubject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.studentsubject.StudentSubjectId;

@Repository
public interface StudentSubjectQueryRepository extends JpaRepository<StudentSubject, StudentSubjectId> {
}
