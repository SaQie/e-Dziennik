package pl.edziennik.application.query.teacher.detailed;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.view.teacher.DetailedTeacherView;
import pl.edziennik.common.valueobject.id.TeacherId;

/**
 * A Query used for getting the detailed teacher information
 * <br>
 * <b>Return DTO: {@link DetailedTeacherView}</b>
 */
@HandledBy(handler = GetDetailedTeacherQueryHandler.class)
public record GetDetailedTeacherQuery(

        TeacherId teacherId

) implements IQuery<DetailedTeacherView> {

    public static final String TEACHER_ID = "teacherId";
}
