package pl.edziennik.application.query.subject;

import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.view.grade.allsubjects.StudentAllSubjectsGradesHeaderView;
import pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView;
import pl.edziennik.common.view.subject.DetailedSubjectView;

import java.util.List;

public interface SubjectQueryDao {

    DetailedSubjectView getDetailedSubjectView(SubjectId subjectId);

    StudentGradesBySubjectView getStudentGradesBySubjectView(SubjectId subjectId, StudentId studentId);

    StudentAllSubjectsGradesHeaderView getAllSubjectsGradesOfSpecificStudentView(StudentId studentId);

    List<StudentGradesBySubjectView> getAllStudentGradesBySubjectView(SubjectId subjectId);


}
