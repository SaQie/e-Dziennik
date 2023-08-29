package pl.edziennik.application.query.subject.specificstudentgrades;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.IQuery;
import pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;

/**
 * A query used for getting the student's subject grades (Grades only from one subject)
 * <br>
 * <b>Return DTO: {@link StudentGradesBySubjectView}</b>
 */
@HandledBy(handler = GetGradesOfSpecificStudentBySubjectHandler.class)
public record GetGradesOfSpecificStudentBySubjectQuery(
        @NotNull SubjectId subjectId,
        @NotNull StudentId studentId
) implements IQuery<StudentGradesBySubjectView> {

    public static final String SUBJECT_ID = "subjectId";
    public static final String STUDENT_ID = "studentId";

}
