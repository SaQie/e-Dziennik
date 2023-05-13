package pl.edziennik.application.query.student.detailed;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.student.DetailedStudentDto;
import pl.edziennik.common.valueobject.id.StudentId;

@HandledBy(handler = GetDetailedStudentQueryHandler.class)
public record GetDetailedStudentQuery(

    StudentId studentId

) implements IQuery<DetailedStudentDto> {

    public static final String STUDENT_ID = "studentId";

}