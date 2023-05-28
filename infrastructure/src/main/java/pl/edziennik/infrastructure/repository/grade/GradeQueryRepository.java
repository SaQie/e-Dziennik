package pl.edziennik.infrastructure.repository.grade;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.dto.file.studentallsubjectsgrades.DetailedGradeForFileDto;
import pl.edziennik.common.dto.file.studentallsubjectsgrades.StudentAllSubjectsGradesHeaderForFileDto;
import pl.edziennik.common.dto.file.studentallsubjectsgrades.StudentAllSubjectsSummaryForFileDto;
import pl.edziennik.common.dto.grade.DetailedGradeDto;
import pl.edziennik.common.dto.grade.allsubjects.StudentAllSubjectsGradesHeaderDto;
import pl.edziennik.common.dto.grade.allsubjects.StudentAssignedSubjectsDto;
import pl.edziennik.common.dto.grade.bysubject.StudentGradesBySubjectDto;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.domain.grade.Grade;

import java.util.List;

@RepositoryDefinition(domainClass = Grade.class, idClass = GradeId.class)
public interface GradeQueryRepository {
    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.grade.bysubject.StudentGradesBySubjectDto(ss.studentSubjectId,st.studentId, st.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.student st " +
            "JOIN ss.subject su " +
            "WHERE su.subjectId = :subjectId")
    List<StudentGradesBySubjectDto> getStudentsGradesBySubjectDto(SubjectId subjectId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.grade.DetailedGradeDto(ss.studentSubjectId,g.gradeId,g.grade,g.weight,g.description,t.teacherId, t.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.subject su " +
            "JOIN Grade g ON g.studentSubject = ss " +
            "JOIN g.teacher t " +
            "WHERE su.subjectId = :subjectId ")
    List<DetailedGradeDto> getDetailedGradeDto(SubjectId subjectId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.grade.DetailedGradeDto(ss.studentSubjectId,g.gradeId,g.grade,g.weight,g.description,t.teacherId, t.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.student st " +
            "JOIN Grade g ON g.studentSubject = ss " +
            "JOIN g.teacher t " +
            "WHERE st.studentId = :studentId ")
    List<DetailedGradeDto> getDetailedGradeDto(StudentId studentId);


    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.grade.bysubject.StudentGradesBySubjectDto(ss.studentSubjectId,st.studentId, st.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.student st " +
            "JOIN ss.subject su " +
            "WHERE su.subjectId = :subjectId " +
            "AND st.studentId = :studentId ")
    StudentGradesBySubjectDto getStudentGradesBySubjectDto(SubjectId subjectId, StudentId studentId);


    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.grade.DetailedGradeDto(ss.studentSubjectId,g.gradeId,g.grade,g.weight,g.description,t.teacherId, t.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.subject su " +
            "JOIN ss.student st " +
            "JOIN Grade g ON g.studentSubject = ss " +
            "JOIN g.teacher t " +
            "WHERE su.subjectId = :subjectId " +
            "AND st.studentId = :studentId ")
    List<DetailedGradeDto> getDetailedGradeDto(SubjectId subjectId, StudentId studentId);


    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.grade.allsubjects.StudentAllSubjectsGradesHeaderDto(s.studentId, s.personInformation.fullName) " +
            "FROM Student s " +
            "WHERE s.studentId = :studentId")
    StudentAllSubjectsGradesHeaderDto getStudentAllSubjectsGradesHeaderDto(StudentId studentId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.grade.allsubjects.StudentAssignedSubjectsDto(ss.studentSubjectId,su.name,t.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.student s " +
            "JOIN ss.subject su " +
            "JOIN su.teacher t " +
            "WHERE s.studentId = :studentId ")
    List<StudentAssignedSubjectsDto> getStudentAssignedSubjectsDto(StudentId studentId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.file.studentallsubjectsgrades.StudentAllSubjectsGradesHeaderForFileDto(sch.name, scl.className, t.personInformation.fullName, s.personInformation.fullName) " +
            "FROM Student s " +
            "JOIN s.school sch " +
            "JOIN s.schoolClass scl " +
            "JOIN scl.teacher t " +
            "WHERE s.studentId = :studentId ")
    StudentAllSubjectsGradesHeaderForFileDto getStudentAllSubjectGradesHeaderForFileDto(StudentId studentId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.file.studentallsubjectsgrades.StudentAllSubjectsSummaryForFileDto(ss.studentSubjectId,su.name,t.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.student s " +
            "JOIN ss.subject su " +
            "JOIN su.teacher t " +
            "WHERE s.studentId = :studentId ")
    List<StudentAllSubjectsSummaryForFileDto> getStudentAllSubjectsSummaryForFileDto(StudentId studentId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.file.studentallsubjectsgrades.DetailedGradeForFileDto(ss.studentSubjectId, g.grade)" +
            "FROM StudentSubject  ss " +
            "JOIN ss.student s " +
            "JOIN Grade g ON g.studentSubject = ss " +
            "WHERE s.studentId = :studentId ")
    List<DetailedGradeForFileDto> getDetailedGradeForFileDto(StudentId studentId);
}
