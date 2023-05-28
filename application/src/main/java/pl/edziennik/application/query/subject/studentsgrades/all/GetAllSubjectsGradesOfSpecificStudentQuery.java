package pl.edziennik.application.query.subject.studentsgrades.all;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.grade.allsubjects.StudentAllSubjectsGradesHeaderDto;
import pl.edziennik.common.valueobject.id.StudentId;

@HandledBy(handler = GetAllSubjectsGradesOfSpecificStudentQueryHandler.class)
public record GetAllSubjectsGradesOfSpecificStudentQuery(
        @NotNull(message = "${student.empty}") StudentId studentId
) implements IQuery<StudentAllSubjectsGradesHeaderDto> {

    public static final String STUDENT_ID = "studentId";

}
