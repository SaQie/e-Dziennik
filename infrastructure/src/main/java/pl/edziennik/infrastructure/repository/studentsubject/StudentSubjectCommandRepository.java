package pl.edziennik.infrastructure.repository.studentsubject;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.StudentSubjectId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.domain.studentsubject.StudentSubject;

@RepositoryDefinition(domainClass = StudentSubject.class, idClass = StudentSubjectId.class)
public interface StudentSubjectCommandRepository {

    @Query("SELECT CASE WHEN COUNT(ss) = 1 THEN TRUE ELSE FALSE END " +
            "FROM StudentSubject ss " +
            "JOIN ss.subject su " +
            "JOIN ss.student st " +
            "WHERE su.subjectId = :subjectId " +
            "AND st.studentId = :studentId")
    boolean isStudentAlreadyAssignedToSubject(StudentId studentId, SubjectId subjectId);

    StudentSubject save(StudentSubject studentSubject);


    StudentSubject getReferenceByStudentStudentIdAndSubjectSubjectId(StudentId studentId, SubjectId subjectId);

    StudentSubject getByStudentStudentIdAndSubjectSubjectId(StudentId studentId, SubjectId subjectId);
}
