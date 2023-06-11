package pl.edziennik.application.query.student.detailed;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.student.DetailedStudentDto;
import pl.edziennik.common.valueobject.id.StudentId;

/**
 * A query used for getting the detailed student information
 * <br>
 * <b>Return DTO: {@link DetailedStudentDto}</b>
 */
@HandledBy(handler = GetDetailedStudentQueryHandler.class)
public record GetDetailedStudentQuery(

    @NotNull(message = "{student.empty}") StudentId studentId

) implements IQuery<DetailedStudentDto> {

    public static final String STUDENT_ID = "studentId";

}
