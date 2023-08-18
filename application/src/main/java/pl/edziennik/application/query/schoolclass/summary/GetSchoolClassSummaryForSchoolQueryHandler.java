package pl.edziennik.application.query.schoolclass.summary;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.schoolclass.SchoolClassSummaryForSchoolView;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassQueryRepository;

@Component
@AllArgsConstructor
class GetSchoolClassSummaryForSchoolQueryHandler implements IQueryHandler<GetSchoolClassSummaryForSchoolQuery, PageView<SchoolClassSummaryForSchoolView>> {

    private final SchoolClassQueryRepository schoolClassQueryRepository;

    @Override
    @Cacheable(value = "schoolClasses", key = "#root.method.name")
    public PageView<SchoolClassSummaryForSchoolView> handle(GetSchoolClassSummaryForSchoolQuery command) {
        Page<SchoolClassSummaryForSchoolView> dto = schoolClassQueryRepository.findAllWithPaginationForSchool(command.pageable(), command.schoolId());

        return PageView.fromPage(dto);
    }
}
