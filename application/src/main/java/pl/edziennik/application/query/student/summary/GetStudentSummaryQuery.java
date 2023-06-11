package pl.edziennik.application.query.student.summary;

import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.student.StudentSummaryDto;

/**
 * A query used for getting the paginated student list (list contains only part of student data)
 * <br>
 * <b>Return DTO: {@link StudentSummaryDto}</b>
 */
@HandledBy(handler = GetStudentSummaryQueryHandler.class)
public record GetStudentSummaryQuery(

        Pageable pageable

) implements IQuery<PageDto<StudentSummaryDto>> {
}
