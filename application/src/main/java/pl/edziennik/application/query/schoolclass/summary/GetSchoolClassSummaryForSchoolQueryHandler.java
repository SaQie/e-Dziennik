package pl.edziennik.application.query.schoolclass.summary;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.schoolclass.SchoolClassSummaryForSchoolDto;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassQueryRepository;

@Component
@AllArgsConstructor
class GetSchoolClassSummaryForSchoolQueryHandler implements IQueryHandler<GetSchoolClassSummaryForSchoolQuery, PageDto<SchoolClassSummaryForSchoolDto>> {

    private final SchoolClassQueryRepository schoolClassQueryRepository;

    @Override
    @Cacheable(value = "schoolClasses", key = "#root.method.name")
    public PageDto<SchoolClassSummaryForSchoolDto> handle(GetSchoolClassSummaryForSchoolQuery command) {
        Page<SchoolClassSummaryForSchoolDto> dto = schoolClassQueryRepository.findAllWithPaginationForSchool(command.pageable(), command.schoolId());

        return PageDto.fromPage(dto);
    }
}
