package pl.edziennik.infrastructure.repositories.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.dto.student.StudentDto;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.domain.student.Student;

@RepositoryDefinition(domainClass = Student.class, idClass = StudentId.class)
public interface StudentQueryRepository {

    @Query("SELECT NEW pl.edziennik.common.dto.student.StudentDto" +
            "(s.studentId, u.userId, u.username, u.email, s.personInformation.fullName, a.address, a.postalCode, a.city, " +
            "s.personInformation.pesel, s.personInformation.phoneNumber, p.parentId, p.personInformation.fullName)" +
            "FROM Student s " +
            "JOIN s.user u " +
            "JOIN s.address a " +
            "LEFT JOIN s.parent p " +
            "WHERE s.studentId = :studentId")
    StudentDto getStudent(StudentId studentId);


    @Query(value = "SELECT NEW pl.edziennik.common.dto.student.StudentDto" +
            "(s.studentId, u.userId, u.username, u.email, s.personInformation.fullName, a.address, a.postalCode, a.city, " +
            "s.personInformation.pesel, s.personInformation.phoneNumber, p.parentId, p.personInformation.fullName)" +
            "FROM Student s " +
            "JOIN s.user u " +
            "JOIN s.address a " +
            "LEFT JOIN s.parent p", countQuery = "SELECT COUNT(s) from Student s")
    Page<StudentDto> findAllWithPagination(Pageable pageable);
}
