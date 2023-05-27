package pl.edziennik.infrastructure.repository.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.dto.student.DetailedStudentDto;
import pl.edziennik.common.dto.student.StudentSummaryDto;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.domain.student.Student;

@RepositoryDefinition(domainClass = Student.class, idClass = StudentId.class)
public interface StudentQueryRepository {

    @Query("SELECT NEW pl.edziennik.common.dto.student.DetailedStudentDto" +
            "(s.studentId, u.userId,s.journalNumber, u.username, u.email, s.personInformation.fullName, a.address, a.postalCode, a.city, " +
            "s.personInformation.pesel, s.personInformation.phoneNumber, p.parentId, p.personInformation.fullName, " +
            "sc.schoolId, sc.name, scl.schoolClassId, scl.className )" +
            "FROM Student s " +
            "JOIN s.user u " +
            "JOIN s.address a " +
            "JOIN s.school sc " +
            "JOIN s.schoolClass scl " +
            "LEFT JOIN s.parent p " +
            "WHERE s.studentId = :studentId")
    DetailedStudentDto getStudent(StudentId studentId);


    @Query(value = "SELECT NEW pl.edziennik.common.dto.student.StudentSummaryDto" +
            "(s.studentId, u.userId, s.journalNumber,u.username, s.personInformation.fullName ,sc.schoolId, sc.name)" +
            "FROM Student s " +
            "JOIN s.user u " +
            "JOIN s.school sc ", countQuery = "SELECT COUNT(s) from Student s")
    Page<StudentSummaryDto> findAllWithPagination(Pageable pageable);
}
