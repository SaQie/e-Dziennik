package pl.edziennik.application.query.teacher.subjects;

import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.teacher.TeacherSubjectsSummaryDto;
import pl.edziennik.common.valueobject.id.TeacherId;

/**
 * A Query used for getting the subjects that the given teacher teach
 * <br>
 * <b>Return DTO: {@link TeacherSubjectsSummaryDto}</b>
 */
@HandledBy(handler = GetTeacherSubjectsSummaryQueryHandler.class)
public record GetTeacherSubjectsSummaryQuery(

        Pageable pageable,
        TeacherId teacherId

) implements IQuery<PageDto<TeacherSubjectsSummaryDto>> {
}
