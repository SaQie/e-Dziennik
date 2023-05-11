package pl.edziennik.application.query.teacher.teachersummary;

import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.teacher.TeacherSummaryDto;

@HandledBy(handler = GetTeacherSummaryQueryHandler.class)
public record GetTeacherSummaryQuery(

        Pageable pageable

) implements IQuery<PageDto<TeacherSummaryDto>> {
}

