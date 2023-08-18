package pl.edziennik.infrastructure.repository.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.view.student.DetailedStudentView;
import pl.edziennik.common.view.student.StudentSummaryView;
import pl.edziennik.domain.student.Student;

@RepositoryDefinition(domainClass = Student.class, idClass = StudentId.class)
public interface StudentQueryRepository {

    @Query("SELECT NEW pl.edziennik.common.view.student.DetailedStudentView" +
            "(s.studentId, u.userId,s.journalNumber, u.username, u.email, s.personInformation.fullName, a.address, a.postalCode, a.city, " +
            "u.pesel, s.personInformation.phoneNumber, p.parentId, p.personInformation.fullName, " +
            "sc.schoolId, sc.name, scl.schoolClassId, scl.className )" +
            "FROM Student s " +
            "JOIN s.user u " +
            "JOIN s.address a " +
            "JOIN s.school sc " +
            "JOIN s.schoolClass scl " +
            "LEFT JOIN s.parent p " +
            "WHERE s.studentId = :studentId")
    DetailedStudentView getStudentView(StudentId studentId);


    @Query(value = "SELECT NEW pl.edziennik.common.view.student.StudentSummaryView" +
            "(s.studentId, u.userId, s.journalNumber,u.username, s.personInformation.fullName ,sc.schoolId, sc.name)" +
            "FROM Student s " +
            "JOIN s.user u " +
            "JOIN s.school sc ", countQuery = "SELECT COUNT(s) from Student s")
    Page<StudentSummaryView> findAllWithPagination(Pageable pageable);
}
