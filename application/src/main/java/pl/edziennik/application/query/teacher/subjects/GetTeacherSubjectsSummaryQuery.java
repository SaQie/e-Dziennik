package pl.edziennik.application.query.teacher.subjects;

import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.IQuery;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacher.TeacherSubjectsSummaryView;
import pl.edziennik.common.valueobject.id.TeacherId;

/**
 * A Query used for getting the subjects that the given teacher teach
 * <br>
 * <b>Return DTO: {@link TeacherSubjectsSummaryView}</b>
 */
@HandledBy(handler = GetTeacherSubjectsSummaryQueryHandler.class)
public record GetTeacherSubjectsSummaryQuery(

        Pageable pageable,
        TeacherId teacherId

) implements IQuery<PageView<TeacherSubjectsSummaryView>> {
}
