package pl.edziennik.application.query.classroom;

import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroom.classroomsforschool.ClassRoomForSchoolView;

/**
 * A query used for getting the paginated class-rooms for school
 * <br>
 * <b>Return VIEW: {@link ClassRoomForSchoolView}</b>
 */
@HandledBy(handler = GetClassRoomForSchoolQueryHandler.class)
public record GetClassRoomForSchoolQuery(
        SchoolId schoolId,
        Pageable pageable
) implements IQuery<PageView<ClassRoomForSchoolView>> {

}
