package pl.edziennik.application.query.subject.studentsgrades.all;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.IQuery;
import pl.edziennik.common.view.grade.allsubjects.StudentAllSubjectsGradesHeaderView;
import pl.edziennik.common.valueobject.id.StudentId;

/**
 * A Query used for getting all assigned student's subjects with grades
 * <br>
 * <b>Return DTO: {@link StudentAllSubjectsGradesHeaderView}</b>
 */
@HandledBy(handler = GetAllSubjectsGradesOfSpecificStudentQueryHandler.class)
public record GetAllSubjectsGradesOfSpecificStudentQuery(
        @NotNull(message = "${student.empty}") StudentId studentId
) implements IQuery<StudentAllSubjectsGradesHeaderView> {

    public static final String STUDENT_ID = "studentId";

}
