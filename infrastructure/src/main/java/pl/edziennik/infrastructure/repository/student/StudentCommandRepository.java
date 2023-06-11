package pl.edziennik.infrastructure.repository.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.student.Student;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Student.class, idClass = StudentId.class)
public interface StudentCommandRepository {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Student s " +
            "JOIN s.parent p " +
            "WHERE s.studentId = :studentId")
    boolean hasAlreadyAssignedParent(StudentId studentId);



    void delete(Student student);

    Optional<Student> findById(StudentId studentId);

    Student save(Student student);

    Student getReferenceById(StudentId studentId);

    Student getByStudentId(StudentId studentId);

    @Query("SELECT s FROM Student s where s.user.userId IN (:userIds)")
    List<Student> getStudentsByUserIds(List<UserId> userIds);

    void deleteAll(Iterable<Student> students);


    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Student s " +
            "JOIN s.user u " +
            "WHERE s.studentId = :studentId " +
            "AND u.isActive = false")
    boolean isStudentAccountNotActive(StudentId studentId);
}
