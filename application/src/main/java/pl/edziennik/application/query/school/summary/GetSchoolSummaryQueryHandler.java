package pl.edziennik.application.query.school.summary;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.school.SchoolSummaryDto;
import pl.edziennik.infrastructure.repositories.school.SchoolQueryRepository;

@Component
@AllArgsConstructor
class GetSchoolSummaryQueryHandler implements IQueryHandler<GetSchoolSummaryQuery, PageDto<SchoolSummaryDto>> {

    private final SchoolQueryRepository schoolQueryRepository;

    @Override
    public PageDto<SchoolSummaryDto> handle(GetSchoolSummaryQuery command) {
        Page<SchoolSummaryDto> dtos = schoolQueryRepository.findAllWithPagination(command.pageable());

        return PageDto.fromPage(dtos);
    }
}
