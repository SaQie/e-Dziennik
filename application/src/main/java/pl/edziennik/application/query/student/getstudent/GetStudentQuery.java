package pl.edziennik.application.query.student.getstudent;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.student.StudentDto;
import pl.edziennik.common.valueobject.id.StudentId;

@HandledBy(handler = GetStudentQueryHandler.class)
public record GetStudentQuery(
    StudentId studentId
) implements IQuery<StudentDto> {

    public static final String STUDENT_ID = "studentId";

}
