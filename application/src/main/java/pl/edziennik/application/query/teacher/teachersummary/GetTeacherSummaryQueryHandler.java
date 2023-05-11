package pl.edziennik.application.query.teacher.teachersummary;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.teacher.TeacherSummaryDto;
import pl.edziennik.infrastructure.repositories.teacher.TeacherQueryRepository;

@Component
@AllArgsConstructor
class GetTeacherSummaryQueryHandler implements IQueryHandler<GetTeacherSummaryQuery, PageDto<TeacherSummaryDto>> {

    private final TeacherQueryRepository teacherQueryRepository;

    @Override
    public PageDto<TeacherSummaryDto> handle(GetTeacherSummaryQuery query) {
        Page<TeacherSummaryDto> dtos = teacherQueryRepository.findAllWithPagination(query.pageable());

        return PageDto.fromPage(dtos);
    }
}
