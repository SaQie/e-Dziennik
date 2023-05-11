package pl.edziennik.application.query.teacher.detailedteacher;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.teacher.DetailedTeacherDto;
import pl.edziennik.common.valueobject.id.TeacherId;

@HandledBy(handler = GetDetailedTeacherQueryHandler.class)
public record GetDetailedTeacherQuery(

        TeacherId teacherId

) implements IQuery<DetailedTeacherDto> {

    public static final String TEACHER_ID = "teacherId";
}
