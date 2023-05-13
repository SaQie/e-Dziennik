package pl.edziennik.application.query.student.summary;

import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.student.StudentSummaryDto;

@HandledBy(handler = GetStudentSummaryQueryHandler.class)
public record GetStudentSummaryQuery(

        Pageable pageable

) implements IQuery<PageDto<StudentSummaryDto>> {
}
