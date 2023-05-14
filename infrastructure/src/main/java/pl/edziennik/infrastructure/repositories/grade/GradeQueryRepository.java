package pl.edziennik.infrastructure.repositories.grade;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.dto.grade.DetailedStudentSubjectGradeDto;
import pl.edziennik.common.dto.grade.StudentGradesSummaryDto;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.domain.grade.Grade;

import java.util.List;

@RepositoryDefinition(domainClass = Grade.class, idClass = GradeId.class)
public interface GradeQueryRepository {
    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.grade.StudentGradesSummaryDto(st.studentId, st.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.student st " +
            "JOIN ss.subject su " +
            "WHERE su.subjectId = :subjectId")
    List<StudentGradesSummaryDto> getStudentGradesSummaryDto(SubjectId subjectId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.grade.DetailedStudentSubjectGradeDto(st.studentId,g.gradeId,g.grade,g.weight,g.description,t.teacherId, t.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.subject su " +
            "JOIN ss.student st " +
            "JOIN Grade g ON g.studentSubject = ss " +
            "JOIN g.teacher t " +
            "WHERE su.subjectId = :subjectId ")
    List<DetailedStudentSubjectGradeDto> getDetailedStudentSubjectDto(SubjectId subjectId);
}
