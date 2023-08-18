package pl.edziennik.infrastructure.repository.grade;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.view.file.studentallsubjectsgrades.DetailedGradeForFileView;
import pl.edziennik.common.view.file.studentallsubjectsgrades.StudentAllSubjectsGradesHeaderForFileView;
import pl.edziennik.common.view.file.studentallsubjectsgrades.StudentAllSubjectsSummaryForFileView;
import pl.edziennik.common.view.grade.DetailedGradeView;
import pl.edziennik.common.view.grade.allsubjects.StudentAllSubjectsGradesHeaderView;
import pl.edziennik.common.view.grade.allsubjects.StudentAssignedSubjectsView;
import pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView;
import pl.edziennik.domain.grade.Grade;

import java.util.List;

@RepositoryDefinition(domainClass = Grade.class, idClass = GradeId.class)
public interface GradeQueryRepository {
    @Query("SELECT NEW " +
            "pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView(ss.studentSubjectId,st.studentId, st.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.student st " +
            "JOIN ss.subject su " +
            "WHERE su.subjectId = :subjectId")
    List<StudentGradesBySubjectView> getStudentsGradesBySubjectView(SubjectId subjectId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.view.grade.DetailedGradeView(ss.studentSubjectId,g.gradeId,g.grade,g.weight,g.description,t.teacherId, t.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.subject su " +
            "JOIN Grade g ON g.studentSubject = ss " +
            "JOIN g.teacher t " +
            "WHERE su.subjectId = :subjectId ")
    List<DetailedGradeView> getDetailedGradeView(SubjectId subjectId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.view.grade.DetailedGradeView(ss.studentSubjectId,g.gradeId,g.grade,g.weight,g.description,t.teacherId, t.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.student st " +
            "JOIN Grade g ON g.studentSubject = ss " +
            "JOIN g.teacher t " +
            "WHERE st.studentId = :studentId ")
    List<DetailedGradeView> getDetailedGradeView(StudentId studentId);


    @Query("SELECT NEW " +
            "pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView(ss.studentSubjectId,st.studentId, st.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.student st " +
            "JOIN ss.subject su " +
            "WHERE su.subjectId = :subjectId " +
            "AND st.studentId = :studentId ")
    StudentGradesBySubjectView getStudentGradesBySubjectView(SubjectId subjectId, StudentId studentId);


    @Query("SELECT NEW " +
            "pl.edziennik.common.view.grade.DetailedGradeView(ss.studentSubjectId,g.gradeId,g.grade,g.weight,g.description,t.teacherId, t.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.subject su " +
            "JOIN ss.student st " +
            "JOIN Grade g ON g.studentSubject = ss " +
            "JOIN g.teacher t " +
            "WHERE su.subjectId = :subjectId " +
            "AND st.studentId = :studentId ")
    List<DetailedGradeView> getDetailedGradeView(SubjectId subjectId, StudentId studentId);


    @Query("SELECT NEW " +
            "pl.edziennik.common.view.grade.allsubjects.StudentAllSubjectsGradesHeaderView(s.studentId, s.personInformation.fullName) " +
            "FROM Student s " +
            "WHERE s.studentId = :studentId")
    StudentAllSubjectsGradesHeaderView getStudentAllSubjectsGradesHeaderView(StudentId studentId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.view.grade.allsubjects.StudentAssignedSubjectsView(ss.studentSubjectId,su.name,t.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.student s " +
            "JOIN ss.subject su " +
            "JOIN su.teacher t " +
            "WHERE s.studentId = :studentId ")
    List<StudentAssignedSubjectsView> getStudentAssignedSubjectsView(StudentId studentId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.view.file.studentallsubjectsgrades.StudentAllSubjectsGradesHeaderForFileView(sch.name, scl.className, t.personInformation.fullName, s.personInformation.fullName) " +
            "FROM Student s " +
            "JOIN s.school sch " +
            "JOIN s.schoolClass scl " +
            "JOIN scl.teacher t " +
            "WHERE s.studentId = :studentId ")
    StudentAllSubjectsGradesHeaderForFileView getStudentAllSubjectGradesHeaderForFileView(StudentId studentId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.view.file.studentallsubjectsgrades.StudentAllSubjectsSummaryForFileView(ss.studentSubjectId,su.name,t.personInformation.fullName) " +
            "FROM StudentSubject ss " +
            "JOIN ss.student s " +
            "JOIN ss.subject su " +
            "JOIN su.teacher t " +
            "WHERE s.studentId = :studentId ")
    List<StudentAllSubjectsSummaryForFileView> getStudentAllSubjectsSummaryForFileView(StudentId studentId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.view.file.studentallsubjectsgrades.DetailedGradeForFileView(ss.studentSubjectId, g.grade)" +
            "FROM StudentSubject  ss " +
            "JOIN ss.student s " +
            "JOIN Grade g ON g.studentSubject = ss " +
            "WHERE s.studentId = :studentId ")
    List<DetailedGradeForFileView> getDetailedGradeForFileView(StudentId studentId);
}
