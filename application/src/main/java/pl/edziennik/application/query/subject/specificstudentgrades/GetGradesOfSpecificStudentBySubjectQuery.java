package pl.edziennik.application.query.subject.specificstudentgrades;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.grade.bysubject.StudentGradesBySubjectDto;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;

/**
 * A query used for getting the student's subject grades (Grades only from one subject)
 * <br>
 * <b>Return DTO: {@link StudentGradesBySubjectDto}</b>
 */
@HandledBy(handler = GetGradesOfSpecificStudentBySubjectHandler.class)
public record GetGradesOfSpecificStudentBySubjectQuery(
        @NotNull SubjectId subjectId,
        @NotNull StudentId studentId
) implements IQuery<StudentGradesBySubjectDto> {

    public static final String SUBJECT_ID = "subjectId";
    public static final String STUDENT_ID = "studentId";

}