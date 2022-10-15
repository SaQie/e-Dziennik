package pl.edziennik.eDziennik.server.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.RatingSubjectStudentLink;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentSubjectRatingsDto;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findByUsername(String username);


}
